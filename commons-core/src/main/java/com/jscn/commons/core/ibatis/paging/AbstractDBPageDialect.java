package com.jscn.commons.core.ibatis.paging;

/**
 * 
 * @Title: AbstractDBPageDialect.java
 * @Description: 抽象的数据库物理分页方言的实现
 * @author kuguobing<kuguobing@snda.com>
 * @date 2010-12-8 下午03:05:47
 * @version V1.0
 */
public abstract class AbstractDBPageDialect implements DBPageDialect {

	public boolean supportPage() {
		return false;
	}

	public boolean supportPageOffset() {
		return supportPage();
	}

	public String getPageQuerySql(String querySelect, long offset, long pagesize) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

	public String getTotalCountQuerySql(String querySelect) {
		return "SELECT COUNT(*) AS _c_ FROM (" + querySelect + ") AS _tc_";
	}

}