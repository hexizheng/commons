package com.jscn.commons.ormbuild.writer;

/**
 * 板路径信息
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: VelocityTempletePathTO.java,v 1.0 2010-6-27 下午04:16:15 lindc Exp $
 * @create		2010-6-27 下午04:16:15
 */
public class VelocityTempletePathTO {
	/**
	 * Velocity模板文件路径
	 */
	private String velocityLoaderPath;
	/**
	 * 包javadoc模板文件名
	 */
	private String packageHtmlVmName;
	/**
	 * 实体类模板文件名
	 */
	private String entityClassVmName;
	/**
	 * 实体SQL模板文件名
	 */
	private String entitySqlVmName;
	/**
	 * DAO测试类模板名称
	 */
	private String entityDAOTestVmName;
	/**
	 * DAO接口类模板名称
	 */
	private String entityDAOVmName;
	/**
	 * DAO实现类模板名称
	 */
	private String entityDAOImplVmName;
	/**
	 * 实体类模板文件名
	 */
	private String entityClassVmNameAbstract;
	/**
	 * 实体SQL模板文件名
	 */
	private String entitySqlVmNameAbstract;
	/**
	 * DAO测试类模板名称
	 */
	private String entityDAOTestVmNameAbstract;
	/**
	 * DAO实现类模板名称
	 */
	private String entityDAOImplVmNameAbstract;

	public VelocityTempletePathTO() {
	}

	/**
	 * @param velocityLoaderPath the velocityLoaderPath to set
	 */
	public void setVelocityLoaderPath(String velocityLoaderPath) {
		this.velocityLoaderPath = velocityLoaderPath;
	}

	/**
	 * @return the velocityLoaderPath
	 */
	public String getVelocityLoaderPath() {
		return velocityLoaderPath;
	}

	/**
	 * @param entityClassVmName the entityClassVmName to set
	 */
	public void setEntityClassVmName(String entityClassVmName) {
		this.entityClassVmName = entityClassVmName;
	}

	/**
	 * @return the entityClassVmName
	 */
	public String getEntityClassVmName() {
		return entityClassVmName;
	}

	/**
	 * @param entitySqlVmName the entitySqlVmName to set
	 */
	public void setEntitySqlVmName(String entitySqlVmName) {
		this.entitySqlVmName = entitySqlVmName;
	}

	/**
	 * @return the entitySqlVmName
	 */
	public String getEntitySqlVmName() {
		return entitySqlVmName;
	}

	/**
	 * @param entityDAOTestVmName the entityDAOTestVmName to set
	 */
	public void setEntityDAOTestVmName(String entityDAOTestVmName) {
		this.entityDAOTestVmName = entityDAOTestVmName;
	}

	/**
	 * @return the entityDAOTestVmName
	 */
	public String getEntityDAOTestVmName() {
		return entityDAOTestVmName;
	}

	/**
	 * @param entityDAOVmName the entityDAOVmName to set
	 */
	public void setEntityDAOVmName(String entityDAOVmName) {
		this.entityDAOVmName = entityDAOVmName;
	}

	/**
	 * @return the entityDAOVmName
	 */
	public String getEntityDAOVmName() {
		return entityDAOVmName;
	}

	/**
	 * @param entityDAOImplVmName the entityDAOImplVmName to set
	 */
	public void setEntityDAOImplVmName(String entityDAOImplVmName) {
		this.entityDAOImplVmName = entityDAOImplVmName;
	}

	/**
	 * @return the entityDAOImplVmName
	 */
	public String getEntityDAOImplVmName() {
		return entityDAOImplVmName;
	}

	/**
	 * @param entityClassVmNameAbstract the entityClassVmNameAbstract to set
	 */
	public void setEntityClassVmNameAbstract(String entityClassVmNameAbstract) {
		this.entityClassVmNameAbstract = entityClassVmNameAbstract;
	}

	/**
	 * @return the entityClassVmNameAbstract
	 */
	public String getEntityClassVmNameAbstract() {
		return entityClassVmNameAbstract;
	}

	/**
	 * @param entitySqlVmNameAbstract the entitySqlVmNameAbstract to set
	 */
	public void setEntitySqlVmNameAbstract(String entitySqlVmNameAbstract) {
		this.entitySqlVmNameAbstract = entitySqlVmNameAbstract;
	}

	/**
	 * @return the entitySqlVmNameAbstract
	 */
	public String getEntitySqlVmNameAbstract() {
		return entitySqlVmNameAbstract;
	}

	/**
	 * @param entityDAOTestVmNameAbstract the entityDAOTestVmNameAbstract to set
	 */
	public void setEntityDAOTestVmNameAbstract(String entityDAOTestVmNameAbstract) {
		this.entityDAOTestVmNameAbstract = entityDAOTestVmNameAbstract;
	}

	/**
	 * @return the entityDAOTestVmNameAbstract
	 */
	public String getEntityDAOTestVmNameAbstract() {
		return entityDAOTestVmNameAbstract;
	}

	/**
	 * @param entityDAOImplVmNameAbstract the entityDAOImplVmNameAbstract to set
	 */
	public void setEntityDAOImplVmNameAbstract(String entityDAOImplVmNameAbstract) {
		this.entityDAOImplVmNameAbstract = entityDAOImplVmNameAbstract;
	}

	/**
	 * @return the entityDAOImplVmNameAbstract
	 */
	public String getEntityDAOImplVmNameAbstract() {
		return entityDAOImplVmNameAbstract;
	}

	public String getPackageHtmlVmName() {
		return packageHtmlVmName;
	}

	public void setPackageHtmlVmName(String packageHtmlVmName) {
		this.packageHtmlVmName = packageHtmlVmName;
	}
}