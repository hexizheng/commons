package com.jscn.commons.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * token工具类
 * @description
 * @author Lincoln
 */
public class ThrowableUtils {

	/**
	 * 获取Throwable信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getThrowableInfo(Throwable t) {
		if (t == null) {
			return null;
		}

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);

		return stringWriter.toString();
	}

	/**
	 * 取得异常的根信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getRootMessage(Throwable t) {
		if (t == null) {
			return "";
		}

		while (true) {
			Throwable cause = t.getCause();
			if (cause == null) {
				String message = t.getMessage();
				if (message != null) {
					return message;
				} else {
					return t.getClass().getName();
				}
			} else {
				t = cause;
			}
		}
	}
}
