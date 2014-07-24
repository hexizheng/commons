package $entityMetaInfoTO.packageName;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;

import com.jscn.commons.core.utils.DateTimeUtils;

/**
 * ${entityMetaInfoTO.eoCommen}访问接口单元测试类
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		${entityMetaInfoTO.createrName} <${entityMetaInfoTO.createEmail}>
 * @version		$Id: ${entityMetaInfoTO.eoDAOTestFileAbstract},v 1.0 ${entityMetaInfoTO.createTime} ${entityMetaInfoTO.createrName} Exp $
 * @create		${entityMetaInfoTO.createTime}
 */
public class ${entityMetaInfoTO.eoDAOTestNameAbstract} extends ${entityMetaInfoTO.daoTestBaseClass}{
	/**
	 * 系统日志输出句柄
	 */
	private final Logger logger = LoggerFactory.getLogger(${entityMetaInfoTO.eoDAOTestNameAbstract}.class);

	/**
	 * ${entityMetaInfoTO.eoCommen}访问接口
	 */
	@Autowired
	private ${entityMetaInfoTO.eoDAOName} dao;
	
	//////////////////////////insert的初始数据//////////////////////////////////////////////////

#foreach( $field in $tableColMetaInfoTOList )
	/**
	 * $field.fieldComment
	 */
	private $field.fieldTypeName $field.testInsertFieldName = $field.testInsertValueInfo;

#end
	
	////////////////////////////update的初始数据/////////////////////////////////////////////////

#foreach( $field in $tableColMetaInfoTOList )
#if(!${field.isId()})
	/**
	 * $field.fieldComment
	 */
	private $field.fieldTypeName $field.testUpdateFieldName = $field.testUpdateValueInfo;

#end
#end

////////////////////////////update field的初始数据/////////////////////////////////////////////////

#foreach( $field in $tableColMetaInfoTOList )
#if(!${field.isId()})
	/**
	 * $field.fieldComment
	 */
	private $field.fieldTypeName $field.testUpdateFieldFieldName = $field.testUpdateFieldValueInfo;

#end
#end

	/**
	 * 测试增删改查操作
	 */
	@Test
	public void testCRUD() {
		//测试插入操作
		Long id = tInsert();

		//测试查询操作
		${entityMetaInfoTO.eoName} selectEO = tSelect(id);;

		//测试更新操作
		tUpdate(id, selectEO);
		
		//测试更新操作
		tUpdateField(id, selectEO);

		//测试删除操作
		tDelete(id, selectEO);
	}

	/**
	 * 测试插入操作
	 * @return
	 */
	private Long tInsert() {
		// 构造对象
		${entityMetaInfoTO.eoName} insertAflEO = new ${entityMetaInfoTO.eoName}();
#foreach( $field in $tableColMetaInfoTOList )
		insertAflEO.${field.setterName}(${field.testInsertFieldName});
#end
		
		//执行插入
		Long id = dao.insert(insertAflEO);
		Assert.assertNotNull(id);

		//判断数据库里数据是否正确
		Map<?, ?> queryAflMap = simpleJdbcTemplate.queryForMap("select * from ${entityMetaInfoTO.tableName} t where t.${entityMetaInfoTO.pkColName}=" + id);
#foreach( $field in $tableColMetaInfoTOList )
#if(${field.isId()})
		Assert.assertEquals(new BigDecimal(id),queryAflMap.get("${field.colName}"));
#elseif (${field.isLastUpdateTime()})
		//assertTrue(System.currentTimeMillis() - ((Timestamp) queryAflMap.get("${field.colName}")).getTime() < TIME_DIFF);
#else
		${field.getTestAssertInfo("queryAflMap",1)};
#end	
#end
		return id;
	}

	/**
	 * 测试查询操作
	 * @param id
	 * @param selectEO
	 * @return 
	 */
	private ${entityMetaInfoTO.eoName} tSelect(Long id) {
		//执行查询
		${entityMetaInfoTO.eoName} selectEO = dao.select(id);
		
		//判断查询出的数据是否正确
#foreach( $field in $tableColMetaInfoTOList )
#if(${field.isId()})
		Assert.assertEquals(id,selectEO.${field.getterName}());
#elseif (${field.isLastUpdateTime()})
		//assertTrue(System.currentTimeMillis() - selectEO.${field.getterName}().getTime() < TIME_DIFF);
#elseif(${field.isVercharType()})
		Assert.assertEquals(${field.testInsertFieldName},selectEO.${field.getterName}().trim());
#else
		Assert.assertEquals(${field.testInsertFieldName},selectEO.${field.getterName}());
#end
#end
		
		return selectEO;
	}

	/**
	 * 测试更新操作
	 * @param id 被更新记录的主键
	 * @param selectEO 被更新的EO对象
	 */
	private void tUpdate(Long id, ${entityMetaInfoTO.eoName} selectEO) {
		// 更改各个字段的值
#foreach( $field in $tableColMetaInfoTOList )
#if(!${field.isVersion()} && !${field.isId()})
		selectEO.${field.setterName}(${field.testUpdateFieldName});
#end
#end
		
		//执行更新操作
		int updateCount = dao.update(selectEO);
		Assert.assertEquals(1,updateCount);

		// 查询数据库是否真的已经更新
		Map<?, ?> queryAflMap = simpleJdbcTemplate.queryForMap("select * from ${entityMetaInfoTO.tableName} t where t.${entityMetaInfoTO.pkColName}=" + id);
#foreach( $field in $tableColMetaInfoTOList )
#if(${field.isId()})
		Assert.assertEquals(new BigDecimal(id),queryAflMap.get("${field.colName}"));
#elseif (${field.isLastUpdateTime()})
		//assertTrue(System.currentTimeMillis() - ((Timestamp) queryAflMap.get("${field.colName}")).getTime() < TIME_DIFF);
#else
		${field.getTestAssertInfo("queryAflMap",2)};
#end	
#end

		// 测试因版本号错误而无法更新
		selectEO.${entityMetaInfoTO.versionSetterMethodName}(selectEO.${entityMetaInfoTO.versionGetterMethodName}() + 1);
		try {
			dao.update(selectEO);
			Assert.assertTrue("此处应该抛出乐观锁异常但未抛出!", false);
		} catch (OptimisticLockingFailureException e) {
			selectEO.${entityMetaInfoTO.versionSetterMethodName}(selectEO.${entityMetaInfoTO.versionGetterMethodName}() - 1);
			logger.info("系统按预期抛出了乐观锁异常:", e);
		}
	}
	
	/**
	 * 测试更新操作
	 * @param id 被更新记录的主键
	 * @param selectEO 被更新的EO对象
	 */
	private void tUpdateField(Long id, ${entityMetaInfoTO.eoName} selectEO) {
		// 更改各个字段的值
#foreach( $field in $tableColMetaInfoTOList )
#if(!${field.isVersion()} && !${field.isId()})
		selectEO.${field.setterName}(${field.testUpdateFieldFieldName});
#end
#end
	
		//执行更新操作
		int updateCount = dao.updateByField(selectEO);
		Assert.assertEquals(1,updateCount);

		// 查询数据库是否真的已经更新
		Map<?, ?> queryAflMap = simpleJdbcTemplate.queryForMap("select * from ${entityMetaInfoTO.tableName} t where t.${entityMetaInfoTO.pkColName}=" + id);
#foreach( $field in $tableColMetaInfoTOList )
#if(${field.isId()})
		Assert.assertEquals(new BigDecimal(id),queryAflMap.get("${field.colName}"));
#elseif (${field.isLastUpdateTime()})
		//assertTrue(System.currentTimeMillis() - ((Timestamp) queryAflMap.get("${field.colName}")).getTime() < TIME_DIFF);
#else
		${field.getTestAssertInfo("queryAflMap",3)};
#end	
#end

		// 测试因版本号错误而无法更新
		selectEO.${entityMetaInfoTO.versionSetterMethodName}(selectEO.${entityMetaInfoTO.versionGetterMethodName}() + 1);
		try {
			dao.updateByField(selectEO);
			Assert.assertTrue("此处应该抛出乐观锁异常但未抛出!", false);
		} catch (OptimisticLockingFailureException e) {
			selectEO.${entityMetaInfoTO.versionSetterMethodName}(selectEO.${entityMetaInfoTO.versionGetterMethodName}() - 1);
			logger.info("系统按预期抛出了乐观锁异常:", e);
		}
	}

	/**
	 * 测试删除操作
	 * @param id 被删除记录的主键
	 * @param selectEO 被删除的EO对象
	 */
	private void tDelete(Long id, ${entityMetaInfoTO.eoName} selectEO) {
		// 测试因版本号错误而无法删除
		selectEO.${entityMetaInfoTO.versionSetterMethodName}(selectEO.${entityMetaInfoTO.versionGetterMethodName}() + 1);
		try {
			dao.delete(selectEO);
			Assert.assertTrue("此处应该抛出乐观锁异常但未抛出!",false);
		} catch (OptimisticLockingFailureException e) {
			selectEO.${entityMetaInfoTO.versionSetterMethodName}(selectEO.${entityMetaInfoTO.versionGetterMethodName}() - 1);
			logger.info("系统按预期抛出了乐观锁异常:", e);
		}

		// 执行删除操作
		dao.delete(selectEO);
		List<?> queryForList = simpleJdbcTemplate.queryForList("select * from ${entityMetaInfoTO.tableName} t where t.${entityMetaInfoTO.pkColName}=" + id);
		Assert.assertEquals(0,queryForList.size());
	}

	/**
	 * 注入被测试的DAO实现
	 * @param dao
	 */
	public void setDao(${entityMetaInfoTO.eoDAOName} dao) {
		this.dao = dao;
	}
}
