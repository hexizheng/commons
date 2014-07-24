/**
 * 
 */
package com.jscn.commons.ormbuild.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * 
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: DataBaseDAOImpl.java,v 1.0 2009-11-15 上午09:50:17 lindc Exp $
 * @create		2009-11-15 上午09:50:17
 */
public class TableMetaInfoDAOImpl  extends SqlMapClientDaoSupport implements TableMetaInfoDAO{

	/* (non-Javadoc)
	 * @see com.mypay.ibatisbuild.database.TableMetaInfoDAO#findTableMetaInfoByTableNamePattern(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TableMetaInfoEO> findTableMetaInfoByTableNamePattern(String[] tableNames, String tableNamePattern) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("tableNames", tableNames);
		paramMap.put("tableNamePattern", tableNamePattern);
		return getSqlMapClientTemplate().queryForList("TableMetaInfoDAO.findTableMetaInfoByTableNamePattern",paramMap);
	}

	/* (non-Javadoc)
	 * @see com.mypay.ibatisbuild.db.tablemetainfo.TableMetaInfoDAO#findTableColMetaInfoByTableName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TableColMetaInfoEO> findTableColMetaInfoByTableName(String tableName) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("tableName", tableName);
		return getSqlMapClientTemplate().queryForList("TableMetaInfoDAO.findTableColMetaInfoByTableName",paramMap);
	}
}
