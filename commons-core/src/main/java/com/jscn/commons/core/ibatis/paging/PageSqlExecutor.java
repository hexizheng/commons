package com.jscn.commons.core.ibatis.paging;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.jscn.commons.core.ibatis.Pagable;

/**
 *Ibatis的分页都是通过滚动ResultSet实现的，应该算是逻辑分页。
 * 逻辑分页虽然能很干净地独立于特定数据库，但效率在多数情况下不及特定数据库支持的物理分页（数据量大的时候还有可能在应用层出现Out Of
 * memory的情况）， 而通过Database Page Dialect分页则是直接组装sql，充分利用了特定数据库的分页机制，效率相对较高。
 * 目前Ibatis的版本是Version:ibatis-2.3.4.726
 * 
 * @Title: PageSqlExecutor.java
 * @Description:直接继承SqlExecutor覆盖executeQuery来静态地实现物理分页
 * @author kuguobing<kuguobing@snda.com>
 * @date 2010-12-8 下午05:02:55
 * @version V1.0
 */
public class PageSqlExecutor extends SqlExecutor implements Pagable {
	private static final Logger logger = Logger
			.getLogger(PageSqlExecutor.class);

	private boolean enablePage = true;

	private DBPageDialect dbPageDialect;

	public DBPageDialect getDbPageDialect() {
		return dbPageDialect;
	}

	public void setDbPageDialect(DBPageDialect dbPageDialect) {
		this.dbPageDialect = dbPageDialect;
	}

	public boolean isEnablePage() {
		return enablePage;
	}

	public void setEnablePage(boolean enablePage) {
		this.enablePage = enablePage;
	}

	public boolean supportPage() {
		if (enablePage && dbPageDialect != null) {
			return dbPageDialect.supportPage();
		}

		return false;
	}

	@Override
	public void executeQuery(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException {
		// 判断是否是分页处理，如果是则重新组装传入的SQL语句，支持物理上分页
		if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
				&& supportPage()) {

			sql = this.dbPageDialect.getPageQuerySql(sql, skipResults,
					maxResults);

			logger.debug(sql);

			// 标识告诉SqlExecutor超类中不要执行分页的处理逻辑判断
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}

		// 否则，什么也不做，直接执行Super的该方法
		super.executeQuery(statementScope, conn, sql, parameters, skipResults,
				maxResults, callback);
	}

}