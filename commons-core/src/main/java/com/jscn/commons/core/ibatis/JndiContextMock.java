/**
 * 
 */
package com.jscn.commons.core.ibatis;

import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.jscn.commons.core.exceptions.SystemException;

/**
 * JNDI上下文环境模拟器
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: JndiContextMock.java,v 1.0 2010-6-23 上午11:38:50 lindc Exp $
 * @create		2010-6-23 上午11:38:50
 */

public class JndiContextMock {

	/**
	 * 上下文内容
	 */
	private Map<String,Object> contextMap;
	
	/**
	 * 初始化JNDI环境
	 */
	public void init(){
		SimpleNamingContextBuilder builder;
		try {
			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		} catch (NamingException e) {
			throw new SystemException("初始化JNDI上下文环境时发生异常:",e);
		}  
		
		
		for (Entry<String,Object> contextEntry : contextMap.entrySet()) {
			builder.bind(contextEntry.getKey(),contextEntry.getValue());  
		}
	}

	public void setContextMap(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
	}
}
