package com.jscn.commons.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于表单验证
 * @author hexizheng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
	String message();
}
