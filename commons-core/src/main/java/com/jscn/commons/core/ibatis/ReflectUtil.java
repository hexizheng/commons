package com.jscn.commons.core.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

/**
 * 
 * @Title: ReflectUtil.java
 * @Description: 对于有些没有公开的set方法，通过Hack Style利用反射绕过Java硬编码的访问控制
 * @author kuguobing<kuguobing@snda.com>
 * @date 2010-12-8 下午05:22:39
 * @version V1.0
 */
public abstract class ReflectUtil {
	private static final Logger logger = Logger.getLogger(ReflectUtil.class);

	private ReflectUtil() {
	}

	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object target, String fname, Class ftype,
			Object fvalue) {
		// 判断出入的参数有效性
		if (target == null
				|| fname == null
				|| "".equals(fname)
				|| (fvalue != null && !ftype
						.isAssignableFrom(fvalue.getClass()))) {
			return;
		}

		// 调用类对应的Setter方法
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("set"
					+ Character.toUpperCase(fname.charAt(0))
					+ fname.substring(1), ftype);

			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}

			method.invoke(target, fvalue);

		} catch (Exception me) {

			// Double Checking
			//logger.debug("没有Setter[" + fname + "]方法：" + me);

			// 如果没有对应的Setter方法，则直接设置对应的类成员变量字段
			try {
				Field field = clazz.getDeclaredField(fname);

				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}

				field.set(target, fvalue);

			} catch (Exception fe) {
				logger.debug(fe);
			}
		}
	}
}
