/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception帮助类
 * 
 * @author 袁兵 2012-3-28
 */
public class ExceptionUtils {
    
    /**
     * 将系统异常对象转字符串
     * @param e 异常对象
     * @return
     */
    public static String exceptionToString(Exception e) {
        StringWriter w = new StringWriter();
        e.printStackTrace(new PrintWriter(w));
        return w.toString().replaceAll("\n", "<br/>").replaceAll("\r", "<br/>")
                .replaceAll("<br/><br/>", "<br/>");
    }
}
