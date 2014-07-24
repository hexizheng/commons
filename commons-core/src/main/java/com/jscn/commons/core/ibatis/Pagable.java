package com.jscn.commons.core.ibatis;

/**
 * 
 * @Title: Pagable.java
 * @Description: 支持物理分页接口
 * @author kuguobing<kuguobing@snda.com>
 * @date 2010-12-13 上午09:31:58
 * @version V1.0
 */
public interface Pagable {

	boolean isEnablePage();

	void setEnablePage(boolean enablePage);
}