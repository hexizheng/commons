/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.trace;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.jscn.commons.core.exceptions.SystemException;

/**
 * 跟踪拦截器
 * 
 * @author 袁兵  2012-3-9
 */
public class TraceInterceptor implements MethodInterceptor, InitializingBean {

    /** logger */
    protected Logger logger;

    /** 日志名 */
    protected String loggerName;

    /** 调用时间阈值 */
    protected long   threshold = 1000L;

    /**
     * {@inheritDoc}
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        String methodName = target.getClass().getSimpleName() + "." + method.getName();
        Object result = null;
        TraceLog traceLog = new TraceLog(this.logger, methodName, this.threshold);

        try {
            traceLog.begin();
            result = invocation.proceed();
        } catch (SystemException appEx) {
            traceLog.setExType("S");
            throw appEx;
        } catch (Throwable t) {
            traceLog.setExType("T");
            throw t;
        } finally {
            traceLog.end();
            traceLog.log();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        if (this.loggerName == null) {
            this.logger = LoggerFactory.getLogger(TraceInterceptor.class);
        } else {
            this.logger = LoggerFactory.getLogger(this.loggerName);
        }
    }

    /**
     * 设置调用时间阈值
     * @param threshold 调用时间阈值
     */
    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    /**
     * 设置日志名
     * @param loggerName 日志名
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

}
