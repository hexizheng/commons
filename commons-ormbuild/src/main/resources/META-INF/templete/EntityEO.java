package $entityMetaInfoTO.packageName;


/**
 * ${entityMetaInfoTO.eoCommen}实体对象
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		${entityMetaInfoTO.createrName} <${entityMetaInfoTO.createEmail}>
 * @version		$Id: ${entityMetaInfoTO.eoFile},v 1.0 ${entityMetaInfoTO.createTime} ${entityMetaInfoTO.createrName} Exp $
 * @create		${entityMetaInfoTO.createTime}
 */
public class ${entityMetaInfoTO.eoName} extends ${entityMetaInfoTO.eoNameAbstract} {
#foreach( $field in $tableColMetaInfoTOList )
#if(${field.isConstants()})
	////////////////////////////////////【$field.fieldComment】常量集////////////////////////////////////////////////
	/**
     * $field.fieldComment:
     */
    @ConstantTag(name = "", type = "$field.colSimpleName")
    public static final $field.fieldTypeName ${field.colSimpleName}_ = "";
    
#end
#end
	////////////////////////////////////【扩张字段】/////////////////////////////////////////////////////////////////////
	



	//////////////////////////////////// 【构造函数集】///////////////////////////////////////////////////
	/**
	 * 默认构造方法
	 */
	public ${entityMetaInfoTO.eoName}(){}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
}