package $entityMetaInfoTO.packageName;

import java.math.BigDecimal;
import java.util.Date;

import com.jscn.commons.core.ibatis.BaseDAO;
/**
 * ${entityMetaInfoTO.eoCommen}实体对象
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		${entityMetaInfoTO.createrName} <${entityMetaInfoTO.createEmail}>
 * @version		$Id: ${entityMetaInfoTO.eoFileAbstract},v 1.0 ${entityMetaInfoTO.createTime} ${entityMetaInfoTO.createrName} Exp $
 * @create		${entityMetaInfoTO.createTime}
 */
public abstract class ${entityMetaInfoTO.eoNameAbstract}  implements java.io.Serializable {
#foreach( $field in $tableColMetaInfoTOList )
	/**
	 * $field.fieldComment
	 */
	protected $field.fieldTypeName $field.fieldName;

#end
#foreach( $field in $tableColMetaInfoTOList )
	/**
	 * 取得 $field.fieldComment
	 */
	public $field.fieldTypeName ${field.getterName}() {
		return $field.fieldName;
	}

	/**
	 * 设置 $field.fieldComment
	 */
	public void ${field.setterName}($field.fieldTypeName $field.fieldName) {
		this.$field.fieldName = $field.fieldName;
	}

#if(${field.isConstants()})
    /**
     * 获取$field.fieldComment名称
     */
    public static String ${field.getterName}Name(String $field.fieldName) {
        return ConstantsUtil.getConstantsName($field.fieldName, "$field.colSimpleName");
    }
    
    /**
     * 获取$field.fieldComment名称
     */
    public String ${field.getterName}Name() {
    	return ${field.getterName}Name($field.fieldName);
    }
#end    
#end
}