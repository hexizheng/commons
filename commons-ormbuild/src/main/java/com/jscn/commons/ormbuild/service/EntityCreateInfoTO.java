package com.jscn.commons.ormbuild.service;

import java.io.File;

/**
 * * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: EntityCreateInfoTO.java,v 1.0 2010-6-27 下午03:26:14 lindc Exp $
 * @create		2010-6-27 下午03:26:14
 */
public class EntityCreateInfoTO {
	
	/**
	 * 序列号类型:SEQ_<主键名>
	 */
	public static final String SEQ_NAME_TYPE_PK="PK"; 
	
	/**
	 * 序列号类型:SEQ_<表名>
	 */
	public static final String SEQ_NAME_TYPE_TABLE_NAME="TABLE_NAME"; 

	/**
	 * 存放JAVA文件的文件夹
	 */
	private String javaPath; 
	
	/**
	 * 存放TEST文件的文件夹
	 */
	private String testPath; 
	
	/**
	 * dao测试基础类名称
	 */
	private String daoTestBaseClass;
	
	public String getDaoTestBaseClass() {
		return daoTestBaseClass;
	}

	public void setDaoTestBaseClass(String daoTestBaseClass) {
		this.daoTestBaseClass = daoTestBaseClass;
	}

	/**
	 * 根包名称
	 */
	private String rootPackageName;
	
	/**
	 * 创建人名称
	 */
	private String createrName; 
	
	/**
	 * 创建人邮箱
	 */
	private String createEmail;
	
	/**
	 * id列名称
	 */
	private String idColName;
	
	/**
	 * version列名称
	 */
	private String versionColName;
	
	/**
	 * updateTime列名称
	 */
	private String updateTimeColName;
	
	/**
	 * 实体类名称(后缀)
	 */
	private String entityName="EO";
	
	/**
	 * 字段是否有类型后缀
	 */
	private boolean hasTypePostfix=true;
	
	/**
	 * 表是否有简称
	 */
	private boolean hasSimpleName;
	
	/**
	 * SEQ名称类型
	 */
	private String seqNameType=EntityCreateInfoTO.SEQ_NAME_TYPE_PK;


	public String getJavaPackagePath(final String subPackageName) {
		return getPackagePath(javaPath, subPackageName);
	}
	
	public String getTestPackagePath(final String subPackageName) {
		return getPackagePath(testPath, subPackageName);
	}

	private String getPackagePath(String srcPath, final String subPackageName) {
		final String rootPackagePath = rootPackageName.replace('.', File.separatorChar);
		final String subPackagePath =  srcPath+ File.separator + rootPackagePath + File.separator + subPackageName;
		return subPackagePath;
	}
	
	public String getPackageName(final String subPackageName){
		return rootPackageName + "." + subPackageName;
	}

	public String getJavaPath() {
		return javaPath;
	}

	public String getRootPackageName() {
		return rootPackageName;
	}

	public String getCreaterName() {
		return createrName;
	}

	public String getCreateEmail() {
		return createEmail;
	}

	public String getTestPath() {
		return testPath;
	}

	public String getIdColName() {
		return idColName;
	}

	public void setIdColName(String idColName) {
		this.idColName = idColName;
	}

	public String getVersionColName() {
		return versionColName;
	}

	public void setVersionColName(String versionColName) {
		this.versionColName = versionColName;
	}

	public String getUpdateTimeColName() {
		return updateTimeColName;
	}

	public void setUpdateTimeColName(String updateTimeColName) {
		this.updateTimeColName = updateTimeColName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public boolean isHasTypePostfix() {
		return hasTypePostfix;
	}

	public void setHasTypePostfix(boolean hasTypePostfix) {
		this.hasTypePostfix = hasTypePostfix;
	}

	public boolean isHasSimpleName() {
		return hasSimpleName;
	}

	public void setHasSimpleName(boolean hasSimpleName) {
		this.hasSimpleName = hasSimpleName;
	}

	public String getSeqNameType() {
		return seqNameType;
	}

	public void setSeqNameType(String seqNameType) {
		this.seqNameType = seqNameType;
	}

	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}

	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}

	public void setRootPackageName(String rootPackageName) {
		this.rootPackageName = rootPackageName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public void setCreateEmail(String createEmail) {
		this.createEmail = createEmail;
	}
}