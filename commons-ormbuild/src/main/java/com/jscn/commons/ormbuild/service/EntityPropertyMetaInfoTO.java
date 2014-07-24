/**
 * 
 */
package com.jscn.commons.ormbuild.service;

import java.math.BigDecimal;

import com.jscn.commons.ormbuild.commons.BuildUtils;
import com.jscn.commons.ormbuild.db.TableColMetaInfoEO;


/**
 * 域的元信息
 * 
 * @author LinDongCheng
 * 
 */
public class EntityPropertyMetaInfoTO {
	////////////////////////////域类型//////////////////////////////////////
	/**
	 * 域类型:String
	 */
	private static final String FIELD_TYPE_STRING = "String";

	/**
	 * 域类型:Date
	 */
	private static final String FIELD_TYPE_DATE = "Date";

	/**
	 * 域类型:BigDecimal
	 */
	private static final String FIELD_TYPE_BIG_DECIMAL = "BigDecimal";
	
	/**
	 * 域类型:Long
	 */
	private static final String FIELD_TYPE_LONG = "Long";

	/**
	 * 域类型:Boolean
	 */
	private static final String FIELD_TYPE_BOOLEAN = "Boolean";

	/////////////////////////////测试类型////////////////////////////////////
	/**
	 * 断言类型:插入操作
	 */
	private static final int TEST_TYPE_INSERT = 1;

	/**
	 * 断言类型:更新操作
	 */
	private static final int TEST_TYPE_UPDATE = 2;

	/**
	 * 断言类型:更新域操作
	 */
	private static final int TEST_TYPE_UPDATE_FIELD = 3;

	/**
	 * 用于测试的value值的前缀(用于尽量避免和程序员初始数据ID冲突)
	 */
	private static final String ID_PREFIX = "20101110133251652";
	
	//////////////////////////////一般属性/////////////////////////////////////////
	
	/**
	 * 域所属的实体的信息
	 */
	final private EntityMetaInfoTO entityMetaInfoTO;
	
	/**
	 * 
	 */
	final private TableColMetaInfoEO tableColMetaInfoEO;
	
	/**
	 * 配置信息
	 */
	private EntityCreateInfoTO eciTO;

	public EntityPropertyMetaInfoTO(EntityMetaInfoTO entityMetaInfoTO, TableColMetaInfoEO tableColMetaInfoEO, EntityCreateInfoTO eciTO) {
		super();
		this.entityMetaInfoTO = entityMetaInfoTO;
		this.tableColMetaInfoEO = tableColMetaInfoEO;
		this.eciTO = eciTO;
	}

	/**
	 * 根据表的列信息构造实体对象属性的元信息
	 * 
	 * @param tableColMetaInfoEO
	 * @param entityMetaInfoTO 
	 * @return
	 */
	public EntityPropertyMetaInfoTO(TableColMetaInfoEO tableColMetaInfoEO, EntityMetaInfoTO entityMetaInfoTO) {
		this.tableColMetaInfoEO=tableColMetaInfoEO;
		this.entityMetaInfoTO=entityMetaInfoTO;
	}

	/**
	 * 根据列类型,取得属性类型
	 * 
	 * @param colType
	 * @param colPrec
	 * @param colScale
	 * @return
	 */
	private String setPropertyType() {
		String colType=tableColMetaInfoEO.getColumnType();
		String colPrecision=tableColMetaInfoEO.getColumnPrecision();
		String colScale=tableColMetaInfoEO.getColumnScale();
		
		if ("NUMBER".equals(colType)) {
		    if("1".equals(colPrecision) && "0".equals(colScale)){
		        return "Boolean";
		    }
		    
			if ("0".equals(colScale)) {
				return "Long";
			} else {
				return "BigDecimal";
			}
		} else if ("DATE".equals(colType) || colType.startsWith("TIMESTAMP")) {
			return "Date";
		} else {
			return "String";
		}
	}

	/**
	 * 判断当前字段是否为VERCHAR类型
	 * 
	 * @return
	 */
	public boolean isVercharType() {
		return getFieldTypeName().equals(FIELD_TYPE_STRING);
	}

	/**
	 * 判断当前字段是否为DATE类型
	 * 
	 * @return
	 */
	public boolean isDateType() {
		return getFieldTypeName().equals(FIELD_TYPE_DATE);
	}

	public String getFieldName() {
		return BuildUtils.getPropertyNameByColName(getColName(),this.eciTO.isHasTypePostfix());
	}

	public String getFieldTypeName() {
		return setPropertyType( );
	}

	public String getFieldComment() {
		return tableColMetaInfoEO.getColumnComment();
	}

	/**
	 * 取得get方法名称
	 * @param fieldName
	 * @return
	 */
	public static String getSetterName(String fileName) {
		return "set" + getFieldNameBig(fileName);
	}
	
	/**
	 * 取得set方法名称
	 * @param fieldName
	 * @return
	 */
	public static String getGetterName(String fileName) {
		return "get" + getFieldNameBig(fileName);
	}
	
	/**
	 * 取得get方法名称
	 * @param fieldName
	 * @return
	 */
	public String getSetterName() {
		return getSetterName(getFieldName());
	}

	/**
	 * 取得set方法名称
	 * @param fieldName
	 * @return
	 */
	public String getGetterName() {
		return getGetterName(getFieldName());
	}

	/**
	 * 根据字段名(首字母小写)取得字段大名(首字母大写)
	 * @param fieldName
	 * @return
	 */
	private String getFieldNameBig() {
		return getFieldNameBig(getFieldName());
	}
	/**
	 * 根据字段名(首字母小写)取得字段大名(首字母大写)
	 * @param fieldName
	 * @return
	 */
	private static String getFieldNameBig(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public String getColName() {
		return tableColMetaInfoEO.getColumnName();
	}

	public String getTestInsertValueInfo() {
		return getTestFieldValue(TEST_TYPE_INSERT);
	}

	public String getTestUpdateValueInfo() {
		return getTestFieldValue(TEST_TYPE_UPDATE);
	}

	public String getTestUpdateFieldValueInfo() {
		return getTestFieldValue(TEST_TYPE_UPDATE_FIELD);
	}

	/**
	 * 取得当前域在各种测试情况下的值
	 * @param testType
	 * @return
	 */
	private String getTestFieldValue(int testType){
		if(isId()){
			return "new Long(\""+ID_PREFIX+testType+"\")";
		}
	    if(FIELD_TYPE_BOOLEAN.equals(getFieldTypeName())){
	        return "true";
	    }
		if(FIELD_TYPE_STRING.equals(getFieldTypeName())){
			return "\""+testType+"\"";
		}
		if(FIELD_TYPE_BIG_DECIMAL.equals(getFieldTypeName())){
			return "new BigDecimal(\""+testType+"\")";
		}
		if(FIELD_TYPE_DATE.equals(getFieldTypeName())){
			return "DateTimeUtils.parseFullDateTime("+getTestDateValue(testType)+")";
		}
		if(FIELD_TYPE_LONG.equals(getFieldTypeName())){
			return "new Long(\""+testType+"\")";
		}
		
		throw new RuntimeException("意料之外的域类型["+getFieldTypeName()+"]");
	}
	
	/**
	 * 取得日期型域的测试值
	 * @param testType
	 * @return
	 */
	private String getTestDateValue(int testType){
		return "\"2010-0"+testType+"-0"+testType+" 0"+testType+":0"+testType+":0"+testType+"\"";
	}

	public String getTestUpdateFieldName() {
		return "update"+getFieldNameBig();
	}

	public String getTestInsertFieldName() {
		return "insert"+getFieldNameBig();
	}

	/**
	 * 取得断言信息
	 * 
	 * @param mapName
	 *            map的名字
	 * @param isInsert
	 *            是否为insert操作的断言(否则就是update操作的断言)
	 * @return
	 */
	public String getTestAssertInfo(String mapName, int assertType) {
		//根据断言类型,判断断言目标值的域名称
		String testFileName = null;
		switch (assertType) {
		case TEST_TYPE_INSERT:
			testFileName = getTestInsertFieldName();
			break;
		case TEST_TYPE_UPDATE:
			testFileName = getTestUpdateFieldName();
			break;
		case TEST_TYPE_UPDATE_FIELD:
			testFileName = getTestUpdateFieldFieldName();
			break;
		default :
			throw new RuntimeException("意料之外的断言类型["+assertType+"]");
		}

		if (FIELD_TYPE_BIG_DECIMAL.equals(getFieldTypeName())) {
			return "Assert.assertEquals(" + testFileName + "," + mapName + ".get(\"" + getColName() + "\"))";
		}
		if (FIELD_TYPE_STRING.equals(getFieldTypeName())) {
			return "Assert.assertEquals(" + testFileName + ",((String)" + mapName + ".get(\"" + getColName() + "\")).trim())";
		}
		if (FIELD_TYPE_LONG.equals(getFieldTypeName())) {
			return "Assert.assertEquals(new BigDecimal(" + testFileName + ")," + mapName + ".get(\"" + getColName() + "\"))";
		}
		if (FIELD_TYPE_DATE.equals(getFieldTypeName())) {
		    return "Assert.assertEquals(DateTimeUtils.formatFullDate(" + testFileName + "),DateTimeUtils.formatFullDate((Timestamp) " + mapName + ".get(\"" + getColName() + "\")))";
		}
		if (FIELD_TYPE_BOOLEAN.equals(getFieldTypeName())) {
		    return "Assert.assertEquals(" + testFileName + ".booleanValue(),BigDecimal.ONE.equals(" + mapName + ".get(\"" + getColName() + "\")))";
		}

		throw new RuntimeException("意料之外的域类型[" + getFieldTypeName() + "]");
	}

	public boolean isId() {
		return getColName().equals(entityMetaInfoTO.getPkColName());
	}

	public boolean isVersion() {
		return getColName().equals(entityMetaInfoTO.getVersionColName());
	}

	public boolean isLastUpdateTime() {
		return getColName().equals(entityMetaInfoTO.getLastUpdateTimeColName());
	}

	public String getColSimpleName() {
		String colName = getColName();
		return this.eciTO.isHasTypePostfix()?colName.substring(0,colName.lastIndexOf("_")):colName;
	}

	public String getTestUpdateFieldFieldName() {
		return "updateField"+getFieldNameBig();
	}


	/**
	 * 判断一个域是否有常量信息
	 * @param fieldName
	 * @return
	 */
	public boolean isConstants() {
		return "CHAR".equals(tableColMetaInfoEO.getColumnType()) && "4".equals(tableColMetaInfoEO.getColumnCharLength());
	}

	public EntityCreateInfoTO getEciTO() {
		return eciTO;
	}

	public void setEciTO(EntityCreateInfoTO eciTO) {
		this.eciTO = eciTO;
	}
}
