package com.jscn.commons.ormbuild.db;

import java.util.List;

/**
 *  * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: TableMetaInfoDAO.java,v 1.0 2009-11-15 上午10:14:25 lindc Exp $
 * @create		2009-11-15 上午10:14:25
 */
public interface TableMetaInfoDAO {

	/**
	 * 根据表名称样式取得表的元信息列表
	 * @param tableNames
	 * @param tableNamePattern TODO
	 * @return
	 */
	List<TableMetaInfoEO> findTableMetaInfoByTableNamePattern(String[] tableNames, String tableNamePattern);

	/**
	 * 取得指定表的列元信息列表
	 * @param tableName
	 * @return
	 */
	List<TableColMetaInfoEO> findTableColMetaInfoByTableName(String tableName);

}