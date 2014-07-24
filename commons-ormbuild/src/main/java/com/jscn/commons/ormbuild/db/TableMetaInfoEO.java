/**
 * 
 */
package com.jscn.commons.ormbuild.db;

/**
 *  * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: TableMetaInfoEO.java,v 1.0 2009-11-15 上午09:53:54 lindc Exp $
 * @create		2009-11-15 上午09:53:54
 */
public class TableMetaInfoEO {
	/**
	 * 根据表名称取得实体对象类名称;
	 * 
	 * @param tableName
	 * @return
	 */
	public String getEntityNameByTableName() {
		String aTableName=tableName.replaceAll("MYPAY_", "").toUpperCase();
		int indexOf = -1;
		StringBuffer buf = new StringBuffer();
		while ((indexOf = aTableName.indexOf("_")) != -1) {
			buf.append(aTableName.charAt(0) + aTableName.substring(1, indexOf).toLowerCase());
			aTableName = aTableName.substring(indexOf + 1);
		}

		return buf.append(aTableName.charAt(0) + aTableName.substring(1).toLowerCase()).toString();
	}
	
	public String getSubPackageName(){
		return getEntityNameByTableName().toLowerCase();
	}

	/**
	 * 表名称
	 */
	private String tableName;
	
	/**
	 * 表描述信息
	 */
	private String tableComment;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
