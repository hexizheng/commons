package com.jscn.commons.ormbuild.main;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jscn.commons.ormbuild.service.EntityCreateInfoTO;
import com.jscn.commons.ormbuild.service.IbatisBuildMainService;

public class IbatisBuildMainServiceMain {
	/**
	 * 系统日志输出句柄
	 */
	private static final Logger log = Logger.getLogger(IbatisBuildMainServiceMain.class);

	public static void main(String[] args) {
		EntityCreateInfoTO eciTO = new EntityCreateInfoTO();
		eciTO.setJavaPath("E:/workspace/customer/customer-dao/src/main/java");
		eciTO.setTestPath("E:/workspace/customer/customer-dao/src/test/java");
		eciTO.setRootPackageName("com.jscn.customer.dao");
		eciTO.setCreaterName("hexizheng");
		eciTO.setCreateEmail("hexizheng@jscntech.com");
		eciTO.setDaoTestBaseClass("com.jscn.customer.dao.testbase.DAOTestBase");
		eciTO.setIdColName("ID");
		eciTO.setVersionColName("VERSION");
		eciTO.setUpdateTimeColName("LAST_UPDATE_TIME");
//		eciTO.setEntityName("DO");
		eciTO.setHasTypePostfix(false);
//		eciTO.setHasSimpleName(false);
		eciTO.setSeqNameType(EntityCreateInfoTO.SEQ_NAME_TYPE_TABLE_NAME);

		//调用服务生产各种映射文件
		String[] tableNames= {"CUSTOMER_INFO","SUBS_INFO","REGION","REGION"};
//		String[] tableNames= {"REGION"};
		String tableNamePattern=null;//"table_name like 'PAYMENT_BASE'";
		boolean generateImpl=false;
		try {
			getBuildService().buildIbatisMapping(tableNames,tableNamePattern, generateImpl, eciTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得构建服务
	 * @return
	 */
	public static IbatisBuildMainService getBuildService() {
		String[] contextFileArr = { "META-INF/ac_ibatisbuild_service.xml" };
		String beanName = "ibatisBuildMainServiceImpl_ibatisbuild";

		IbatisBuildMainService ibatisBuildMainService=null;
		try {
			ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
			ibatisBuildMainService = (IbatisBuildMainService) appCont.getBean(beanName);
		} catch (Throwable e) {
			log.fatal("", e);
		}
		if (ibatisBuildMainService == null) {
			log.error("无法获取bean:" + beanName);
		}
		return ibatisBuildMainService;
	}
	
	
}
