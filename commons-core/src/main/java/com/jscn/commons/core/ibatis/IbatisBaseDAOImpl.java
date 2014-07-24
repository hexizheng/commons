/**
 * 
 */
package com.jscn.commons.core.ibatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

/**
 * IBatis方式实现的数据访问服务基础类
 * 
 * 设置SqlExecutor属性为{@link} PageSqlExecutor，使其查询时支持数据库物理分页功能
 * 
 * @author LinDongCheng
 * 
 */
public class IbatisBaseDAOImpl {
	/**
	 * 数据库写操作处理器
	 */
	private SqlMapClientTemplate updateSqlMapClientTemplate;

	/**
	 * 数据库读操作处理器
	 */
	private SqlMapClientTemplate[] selectSqlMapClientTemplateArr;

	/**
	 * 查询用SQL MAP索引标识
	 */
	private int selectSqlMapIndex;

	/**
	 * 查询用SQL MAP的总个数
	 */
	private int countOfSelectSqlMap;

	/**
	 * 注入Paging Sql Executor覆盖Ibatis中的SqlExecutor
	 */
	private SqlExecutor sqlExecutor;

	/**
	 * 取得下一个查询用SQL MAP;
	 * 
	 * @return
	 */
	private SqlMapClientTemplate getNextSelectSqlMapClientTemplate() {
		selectSqlMapIndex = (selectSqlMapIndex + 1) % countOfSelectSqlMap;
		return selectSqlMapClientTemplateArr[selectSqlMapIndex];
	}

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public void setEnablePage(boolean enablePage) {
		if (sqlExecutor instanceof Pagable) {
			((Pagable) sqlExecutor).setEnablePage(enablePage);
		}
	}

	/**
	 * 初始化访问对象
	 */
	public void init() {
		countOfSelectSqlMap = selectSqlMapClientTemplateArr.length;
		selectSqlMapIndex = 0;

		// Initialization时
		// 反射注入设置所有查询SqlMapClientTemplate'SqlMapClient的sqlExecutor属性
		Assert.notNull(sqlExecutor, "需要指定分页的Ibatis SqlExecutor！");
		for (SqlMapClientTemplate sqlMapClientTemplate : selectSqlMapClientTemplateArr) {
			// 由于sqlExecutor是com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient::getDelegate()的私有保护成员变量，
			// 且没有公开的set方法，所以此处通过反射绕过java的访问控制
			// Hack setting & replace sqlMapClient --> deltegate --> sqlExecutor
			SqlMapClient sqlMapClient = sqlMapClientTemplate.getSqlMapClient();
			if (sqlMapClient instanceof ExtendedSqlMapClient) {
				ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(), "sqlExecutor", SqlExecutor.class, sqlExecutor);
			}
		}
	}

	public int delete(String statementName, Object parameterObject) throws DataAccessException {
		return updateSqlMapClientTemplate.delete(statementName, parameterObject);
	}

	public int delete(String statementName) throws DataAccessException {
		return updateSqlMapClientTemplate.delete(statementName);
	}

	public Object insert(String statementName, Object parameterObject) throws DataAccessException {
		return updateSqlMapClientTemplate.insert(statementName, parameterObject);
	}

	/**
	 * 批量插入对象
	 * 
	 * @param statementName
	 *            插入语句名
	 * @param objList
	 *            对象列表
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Object> insertBatch(final String statementName, final List<Object> objList) throws DataAccessException {
		return (List<Object>) updateSqlMapClientTemplate.execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				List<Object> insertReturnList = new ArrayList<Object>();
				for (Object object : objList) {
					insertReturnList.add(executor.insert(statementName, object));
				}
				executor.executeBatch();
				return insertReturnList;
			}
		});
	}

	public Object insert(String statementName) throws DataAccessException {
		return updateSqlMapClientTemplate.insert(statementName);
	}

	public void update(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
		updateSqlMapClientTemplate.update(statementName, parameterObject, requiredRowsAffected);
	}

	public int update(String statementName, Object parameterObject) throws DataAccessException {
		return updateSqlMapClientTemplate.update(statementName, parameterObject);
	}

	public int update(String statementName) throws DataAccessException {
		return updateSqlMapClientTemplate.update(statementName);
	}

	@SuppressWarnings("unchecked")
	public List queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForList(statementName, skipResults, maxResults);
	}

	@SuppressWarnings("unchecked")
	public List queryForList(String statementName, Object parameterObject, int skipResults, int maxResults) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForList(statementName, parameterObject, skipResults, maxResults);
	}

	@SuppressWarnings("unchecked")
	public List queryForList(String statementName, Object parameterObject) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}

	@SuppressWarnings("unchecked")
	public List queryForList(String statementName) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForList(statementName);
	}

	public Object queryForObject(String statementName, Object parameterObject, Object resultObject) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForObject(statementName, parameterObject, resultObject);
	}

	public Object queryForObject(String statementName, Object parameterObject) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForObject(statementName, parameterObject);
	}

	public Object queryForObject(String statementName) throws DataAccessException {
		return getNextSelectSqlMapClientTemplate().queryForObject(statementName);
	}

	public void setUpdateSqlMapClientTemplate(SqlMapClient updateSqlMapClient) {

		this.updateSqlMapClientTemplate = new SqlMapClientTemplate(updateSqlMapClient);
	}

	public void setSelectSqlMapClientTemplateArr(SqlMapClient[] selectSqlMapClientArr) {
		selectSqlMapClientTemplateArr = new SqlMapClientTemplate[selectSqlMapClientArr.length];
		for (int i = 0; i < selectSqlMapClientArr.length; i++) {
			selectSqlMapClientTemplateArr[i] = new SqlMapClientTemplate(selectSqlMapClientArr[i]);
		}
	}

	protected SqlMapClientTemplate getUpdateSqlMapClientTemplate() {
		return updateSqlMapClientTemplate;
	}
}
