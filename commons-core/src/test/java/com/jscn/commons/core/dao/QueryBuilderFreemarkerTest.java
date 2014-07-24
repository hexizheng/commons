/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.dao;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试freemarker模板解析器
 *
 * @author 袁兵  2012-3-9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-test.xml" })
public class QueryBuilderFreemarkerTest {
    
    @Autowired
    QueryBuilder queryBuilder;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testGetQueryString(){
        
        Map root = new HashMap();
        root.put("user", "Big Joe");
        String actual = queryBuilder.getQueryString("testUser", root);
        System.out.print(actual);
        Assert.assertEquals("Big Joe", actual);
    }
    
    @Test
    public void testComplexString(){
        Map root = new HashMap();
        root.put("blNo", "B20120309");
        root.put("orderNo", "O20120309");
        root.put("operator", "M<J>R/");
//        root.put("beginDate", "20120309");
//        root.put("endDate", "20120318");
        root.put("orgIds", "(1,2,3,4,8)");
        String actual = queryBuilder.getQueryString("complexString", root);
        System.out.print(actual);
    }
}
