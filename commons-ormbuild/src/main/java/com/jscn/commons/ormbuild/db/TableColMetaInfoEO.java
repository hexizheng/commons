/**
 * 
 */
package com.jscn.commons.ormbuild.db;

/**
 * * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: TableColMetaInfoEO.java,v 1.0 2009-11-15 上午10:17:27 lindc Exp $
 * @create		2009-11-15 上午10:17:27
 */
public class TableColMetaInfoEO {

	/**
	 * 列名称
	 */
	private String columnName;
	
	/**
	 * 列描述
	 */
	private String columnComment;
	
	/**
	 * 列类型 
	 */
	private String columnType;
	
	/**
	 * 列精确度
	 */
	private String columnPrecision;
	
	/**
	 * 列刻度
	 */
	private String columnScale;
	
	/**
	 * 字符型长度
	 */
	private String columnCharLength;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnPrecision() {
		return columnPrecision;
	}

	public void setColumnPrecision(String columnPrecision) {
		this.columnPrecision = columnPrecision;
	}

	public String getColumnScale() {
		return columnScale;
	}

	public void setColumnScale(String columnScale) {
		this.columnScale = columnScale;
	}

	public String getColumnCharLength() {
		return columnCharLength;
	}

	public void setColumnCharLength(String columnCharLength) {
		this.columnCharLength = columnCharLength;
	}
}
