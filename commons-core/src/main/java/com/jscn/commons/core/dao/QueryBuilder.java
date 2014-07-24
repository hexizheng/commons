/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.dao;

import java.util.Map;

/**
 * 查询SQL生成器接口
 *
 * @author 袁兵  2012-3-9
 */
public interface QueryBuilder {
    /**
     * 获取查询语句
     * @param queryName 查询名
     * @param conditions 条件
     * @return 查询语句
     */
    String getQueryString(String queryName, Map<String, ?> conditions);
    
    /**
     * 获取查询语句
     * @param queryName 查询名
     * @param queryTemplate 原始sql模板
     * @param conditions 条件
     * @return 查询语句
     */
    String getQueryString(String queryName, String queryTemplate, Map<String, ?> conditions);
}
