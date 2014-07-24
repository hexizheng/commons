package com.jscn.commons.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于解析xml
 * @author hexizheng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementName {
	String name();
	String attr() default "";//要查找的属性名
	boolean loop() default true;

}
