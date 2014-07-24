package com.jscn.commons.core.ibatis.paging;

/**
 * 
 * @Title: OraclePageDialect.java
 * @Description: An SQL dialect for Oracle 9 -10.X (uses ANSI-style syntax where
 *               possible)
 * @author kuguobing<kuguobing@snda.com>
 * @date 2010-12-8 下午05:50:15
 * @version V1.0
 */
public class OraclePageDialect extends AbstractDBPageDialect implements
		DBPageDialect {

	@Override
	public String getPageQuerySql(String querySelect, long offset, long pagesize) {
		StringBuffer pagingSelect = new StringBuffer(querySelect.length() + 100);

		if (offset > 0) {
			long startIndex = offset;
			long endIndex = offset + pagesize;
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
			pagingSelect.append(querySelect);
			pagingSelect.append(" ) row_ where rownum <= " + endIndex
					+ ") where rownum_ > " + startIndex);
		} else {
			pagingSelect.append("select * from ( ");
			pagingSelect.append(querySelect);
			pagingSelect.append(" ) where rownum <= " + pagesize);
		}

		return pagingSelect.toString();
	}

	@Override
	public boolean supportPage() {
		return true;
	}

}