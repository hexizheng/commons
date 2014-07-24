package com.jscn.commons.ormbuild.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jscn.commons.ormbuild.commons.BuildUtils;
import com.jscn.commons.ormbuild.db.TableMetaInfoEO;


public class EntityMetaInfoTO {
	
	/**
	 * 数据库表元信息
	 */
	final private TableMetaInfoEO tableMetaInfoEO;
	
	/**
	 * 实体创建信息
	 */
	final private EntityCreateInfoTO eciTO;

	public EntityCreateInfoTO getEciTO() {
		return eciTO;
	}

	/**
	 * 表的简称
	 */
	private String tableSimpleName=null;
	
	/**
	 * 根据表元信息构造实体对象元信息
	 * 
	 * @param tableMetaInfoEO
	 *            数据表元信息
	 * @param eciTO 实体创建信息
	 * @param tableSimpleName
	 *            表简称
	 * @return
	 */
	public EntityMetaInfoTO(TableMetaInfoEO tableMetaInfoEO,EntityCreateInfoTO eciTO,String anyColName) {
		this.tableMetaInfoEO=tableMetaInfoEO;
		this.eciTO=eciTO;
		int simpleNameEndIndex = anyColName.indexOf("_");
		if(simpleNameEndIndex!=-1){
			this.tableSimpleName = anyColName.substring(0, simpleNameEndIndex);
		}
	}

	public String getDaoTestBaseClass() {
		return eciTO.getDaoTestBaseClass();
	}

	public String getEoName() {
		return getEntitySimpleName() + eciTO.getEntityName();
	}
	
	public String getEoFile() {
		return getEoName()+".java";
	}
	
	public String getEoPath() {
		return getJavaPath(getEoFile());
	}
	
	public String getPackageHtmlPath() {
		return getJavaPath("package.html");
	}
	
	public String getEoFileAbstract() {
		return getEoNameAbstract()+".java";
	}
	
	public String getEoPathAbstract() {
		return getJavaPath(getEoFileAbstract());
	}

	public String getEoCommen() {
		return tableMetaInfoEO.getTableComment();
	}

	public String getTableName() {
		return tableMetaInfoEO.getTableName();
	}

	public String getPackageName() {
		return eciTO.getPackageName(tableMetaInfoEO.getSubPackageName());
	}

	public String getJavaPackagePath() {
		return eciTO.getJavaPackagePath(tableMetaInfoEO.getSubPackageName());
	}
	
	public String getTestPackagePath() {
		return eciTO.getTestPackagePath(tableMetaInfoEO.getSubPackageName());
	}

	public String getCreateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public String getCreaterName() {
		return eciTO.getCreaterName();
	}

	public String getCreateEmail() {
		return eciTO.getCreateEmail();
	}
	
	public String getEoFullName() {
		return getPackageName()+"."+getEoName();
	}

	public String getEoDAOName() {
		return getEntitySimpleName() + "DAO";
	}
	
	public String getEoDAOInstName() {
		return getEoDAOName().substring(0,1).toLowerCase()+getEoDAOName().substring(1);
	}
	
	public String getEoDAOFile() {
		return getEoDAOName()+".java";
	}
	
	public String getEoDAOPath() {
		return getJavaPath(getEoDAOFile());
	}
	
	private String getJavaPath(String fileName) {
		return getJavaPackagePath()+File.separator+fileName;
	}
	
	private String getTestPath(String fileName) {
		return getTestPackagePath()+File.separator+fileName;
	}

	public String getEoSqlName() {
		return getEntitySimpleName();
	}
	
	public String getEoSqlFile() {
		return getEoSqlName()+".xml";
	}
	
	public String getEoSqlPath() {
		return getJavaPath(getEoSqlFile());
	}
	public String getEoSqlFileAbstract() {
		return getEoSqlNameAbstract()+".xml";
	}
	
	public String getEoSqlPathAbstract() {
		return getJavaPath(getEoSqlFileAbstract());
	}

	public String getPkColName() {
		String colName = eciTO.getIdColName();
		if(StringUtils.isBlank(colName)){
			return tableSimpleName + "_ID_N";
		}else{
			return colName;
		}
	}

	public String getVersionColName() {
		String colName = eciTO.getVersionColName();
		if(StringUtils.isBlank(colName)){
			return tableSimpleName + "_VERSION_N";
		}else{
			return colName;
		}
	}

	public String getLastUpdateTimeColName() {
		String colName = eciTO.getUpdateTimeColName();
		if(StringUtils.isBlank(colName)){
			return tableSimpleName + "_LAST_UPDATE_TIME_T";
		}else{
			return colName;
		}
	}

	public String getPkPropertyName() {
		return BuildUtils.getPropertyNameByColName(getPkColName(), eciTO.isHasTypePostfix());
	}
	
	public String getPkSetterName() {
		return "set"+getPkPropertyName().substring(0,1).toUpperCase()+getPkPropertyName().substring(1);
	}
	
	public String getPkGetterName() {
		return "get"+getPkPropertyName().substring(0,1).toUpperCase()+getPkPropertyName().substring(1);
	}

	public String getVersionPropertyName() {
		return BuildUtils.getPropertyNameByColName(getVersionColName(), eciTO.isHasTypePostfix());
	}

	public String getLastUpdateTimePropertyName() {
		return BuildUtils.getPropertyNameByColName(getLastUpdateTimeColName(), eciTO.isHasTypePostfix());
	}

	public String getSeqName(){
		String seqPrefix = "SEQ_";
		String seqNameType = eciTO.getSeqNameType();
		if(EntityCreateInfoTO.SEQ_NAME_TYPE_PK.equals(seqNameType)){
			return seqPrefix+this.getPkColName();
		}
		if(EntityCreateInfoTO.SEQ_NAME_TYPE_TABLE_NAME.equals(seqNameType)){
			return seqPrefix+this.getTableName();
		}
		
		throw new RuntimeException("意料之外的SEQ名称类型【"+seqNameType+"】");
	}
	
	public String getEoDAOImplName() {
		return getEoSqlName() + "DAOImpl";
	}
	
	public String getEoDAOImplFile() {
		return getEoDAOImplName()+".java";
	}
	
	public String getEoDAOImplPath() {
		return getJavaPath(getEoDAOImplFile());
	}
	
	public String getEoDAOImplFileAbstract() {
		return getEoDAOImplNameAbstract()+".java";
	}
	
	public String getEoDAOImplPathAbstract() {
		return getJavaPath(getEoDAOImplFileAbstract());
	}

	public String getEoDAOTestName() {
		return getEoSqlName()+ "ITest";
	}
	
	public String getEoDAOTestFile() {
		return getEoDAOTestName()+".java";
	}
	
	public String getEoDAOTestPath() {
		return getTestPath(getEoDAOTestFile());
	}
	public String getEoDAOTestFileAbstract() {
		return getEoDAOTestNameAbstract()+".java";
	}
	
	public String getEoDAOTestPathAbstract() {
		return getTestPath(getEoDAOTestFileAbstract());
	}

	public String getVersionGetterMethodName() {
		return EntityPropertyMetaInfoTO.getGetterName(getVersionPropertyName());
	}

	public String getVersionSetterMethodName() {
		return EntityPropertyMetaInfoTO.getSetterName(getVersionPropertyName());
	}

	public String getEoSqlNameAbstract() {
		return getEntitySimpleName()+"Abstract";
	}

	public String getEoDAOImplNameAbstract() {
		return getEoSqlName() + "DAOImplAbstract";
	}

	public String getEoDAOTestNameAbstract() {
		return getEoSqlName() + "ITestAbstract";
	}

	public String getEoNameAbstract() {
		return getEntitySimpleName()+eciTO.getEntityName() + "Abstract";
	}

	public String getEoDAONameAbstract() {
		return getEntitySimpleName() + "DAOAbstract";
	}

	public String getEntitySimpleName() {
		return tableMetaInfoEO.getEntityNameByTableName();
	}
}