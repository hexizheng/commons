package $entityMetaInfoTO.packageName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;

/**
 * ${entityMetaInfoTO.eoCommen}访问接口单元测试类
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		${entityMetaInfoTO.createrName} <${entityMetaInfoTO.createEmail}>
 * @version		$Id: ${entityMetaInfoTO.eoDAOTestFile},v 1.0 ${entityMetaInfoTO.createTime} ${entityMetaInfoTO.createrName} Exp $
 * @create		${entityMetaInfoTO.createTime}
 */
public class ${entityMetaInfoTO.eoDAOTestName} extends ${entityMetaInfoTO.daoTestBaseClass}{
	/**
	 * 系统日志输出句柄
	 */
	public final Logger logger = LoggerFactory.getLogger(${entityMetaInfoTO.eoDAOTestName}.class);

	/**
	 * ${entityMetaInfoTO.eoCommen}访问接口
	 */
	@Autowired
	protected ${entityMetaInfoTO.eoDAOName} dao;

	/**
	 * 演示用测试
	 */
	@Test
	public void testDemo(){
		logger.debug("${entityMetaInfoTO.eoDAOTestName} demo test!");
	}
	
	/**
	 * 注入被测试的DAO实现
	 * @param dao
	 */
	public void setDao(${entityMetaInfoTO.eoDAOName} dao) {
		this.dao = dao;
	}
}
