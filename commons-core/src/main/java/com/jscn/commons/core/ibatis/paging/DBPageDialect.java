package com.jscn.commons.core.ibatis.paging;

/**
 * 
 * @Title: DBPageDialect.java
 * @Description: 数据库物理分页方言接口
 * @author kuguobing<kuguobing@snda.com>
 * @date 2010-12-8 下午03:09:31
 * @version V1.0
 */
public interface DBPageDialect {
	long UNLIMIT_SIZE = -1L;

	long DEFAULT_PAGE_SIZE = 20;

	boolean supportPage();

	boolean supportPageOffset();

	String getPageQuerySql(String querySelect, long offset, long pagesize);

	String getTotalCountQuerySql(String querySelect);
}