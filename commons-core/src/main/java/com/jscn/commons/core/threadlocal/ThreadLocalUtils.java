package com.jscn.commons.core.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前线程工具类
 * @description
 * @author hexizheng
 */

public class ThreadLocalUtils {
	
	private static final String CURRENT_USER = "CURRENT_USER";

	/**
	 * 保持当前线程独有信息的对象
	 */
	private static final ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal<Map<Object, Object>>();

	/**
	 * 往当前线程中存放信息
	 * 
	 * @param key
	 * @param value
	 */
	private static void setValue(Object key, Object value) {
		Map<Object, Object> threadLocalValueMap = threadLocal.get();
		if (threadLocalValueMap == null) {
			threadLocalValueMap = new HashMap<Object, Object>();
			threadLocal.set(threadLocalValueMap);
		}
		threadLocalValueMap.put(key, value);
	}

	/**
	 * 从当前线程中取得信息
	 * 
	 * @param key
	 * @return
	 */
	private static Object getValue(Object key) {
		Map<Object, Object> threadLocalValueMap = threadLocal.get();
		if (threadLocalValueMap == null) {
			return null;
		} else {
			return threadLocalValueMap.get(key);
		}
	}

	/**
	 * 取得当前用户名称
	 * 
	 * @return
	 */
	public static String getUserName() {
		CurrentUser user = getCurrentUser();
		if(user == null){
	        return null; 
	    }
		return user.getUserName();
	}

	/**
	 * 取得当前用户编号
	 * 
	 * @return
	 */
	public static String getUserId() {
		CurrentUser user = getCurrentUser();
		if(user == null){
	        return null; 
	    }
		return user.getUserId();
	}
	/**
	 * 取得当前用户类型
	 * 
	 * @return
	 */
	public static String getUserType() {
		CurrentUser user = getCurrentUser();
		if(user == null){
	        return null; 
	    }
		return user.getUserType();
	}
	
	
	/**
	 * 取得当前用户IP
	 * 
	 * @return
	 */
	public static String getUserIP() {
		CurrentUser user = getCurrentUser();
		if(user == null){
	        return null; 
	    }
		return user.getIp();
	}
	
	
	/**
	 * @param 
	 */
	public static void setCurrentUser(CurrentUser user) {
		setValue(CURRENT_USER, user);
	}

	/**
	 * @param 
	 */
	public static CurrentUser getCurrentUser() {
		return (CurrentUser) getValue(CURRENT_USER);
	}
	
	
}
