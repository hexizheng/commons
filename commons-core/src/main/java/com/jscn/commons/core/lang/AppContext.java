/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.jscn.commons.core.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用上下文<br>
 * 利用线程变量存储上下文
 * 
 * @author 袁兵 2012-3-9
 */
public class AppContext {

    /** 应用上下文线程变量 */
    private static final ThreadLocal<AppContext> appContextHolder = new ThreadLocal<AppContext>();

    /** 上下文中的属性 */
    protected Map<String, Object>                properties       = new HashMap<String, Object>();

    /**
     * 获取应用上下文
     * @return appContext 应用上下文
     */
    public static AppContext get() {
        AppContext appContext = appContextHolder.get();

        if (appContext == null) {
            appContext = new AppContext();
            appContextHolder.set(appContext);
        }

        return appContext;
    }

    /**
     * 增加属性
     * @param key 属性键
     * @param value 属性值
     * @return 属性值
     */
    public Object put(String key, Object value) {
        return this.properties.put(key, value);
    }

    /**
     * 删除属性
     * @param key 属性键
     * @return 属性值
     */
    public Object remove(String key) {
        return this.properties.remove(key);
    }

    /**
     * 获取属性
     * @param key 属性键
     * @return 属性值
     */
    public Object get(String key) {
        return this.properties.get(key);
    }

    /**
     * 清除上下文中的属性
     */
    public void clear() {
        Map<String, Object> map = appContextHolder.get().properties;
        if (map != null) {
            map.clear();
        }
        appContextHolder.remove();
    }

}
