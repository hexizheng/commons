/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.dao;

import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jscn.commons.core.exceptions.SystemException;
import com.jscn.commons.core.lang.ErrorCode;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Freemarker查询SQL生成器
 * 
 * @author 袁兵 2012-3-9
 */
public class QueryBuilderFreemarker implements QueryBuilder, ApplicationContextAware,
        InitializingBean {

    /** logger */
    private static final Logger    logger = LoggerFactory.getLogger(QueryBuilderFreemarker.class);

    /** Spring上下文 */
    protected ApplicationContext   applicationContext;

    /** freemarker configuration */
    protected Configuration        cfg;

    /** freemarker template loader */
    protected StringTemplateLoader stringTL;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getQueryString(String queryName, Map<String, ?> conditions) {
        String queryTemplate = (String) this.applicationContext.getBean(queryName);

        return getQueryString(queryName, queryTemplate, conditions);
    }

    /**
     * {@inheritDoc}
     */
    public String getQueryString(String queryName, String queryTemplate, Map<String, ?> conditions) {
        if (queryTemplate == null) {
            throw new SystemException(ErrorCode.ERROR_QUERY_TEMPLATE_NOT_EXIST, "查询模板不存在！");
        }
        try {
            stringTL.putTemplate(queryName, queryTemplate);
            Template temp = cfg.getTemplate(queryName);
            StringWriter queryStringWriter = new StringWriter();
            temp.process(conditions, queryStringWriter);
            String queryString = queryStringWriter.toString();
            return queryString == null ? null : queryString.trim();
        } catch (Exception ex) {
            logger.error("Freemarker引擎生成查询语句出错！", ex);
            throw new SystemException(ErrorCode.ERROR_BUILD_QUERY_STRING, "Freemarker引擎生成查询语句出错！",
                    ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        cfg = new Configuration();
        stringTL = new StringTemplateLoader();
        cfg.setTemplateLoader(stringTL);
        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }

}
