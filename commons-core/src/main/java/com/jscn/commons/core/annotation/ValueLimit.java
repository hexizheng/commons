package com.jscn.commons.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 表单验证 值大小
 * @author 贺夕政
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueLimit {
	long min() default -999999999;
	long max() default  999999999;
	String message();
}
