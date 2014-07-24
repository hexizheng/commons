/**
 * 
 */
package com.jscn.commons.ormbuild.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.jscn.commons.ormbuild.service.EntityMetaInfoTO;
import com.jscn.commons.ormbuild.service.EntityPropertyMetaInfoTO;

/**
 * 件生成器
 * 
 * @description
 * @usage
 * @copyright Copyright 2010 SDO Corporation. All rights reserved.
 * @company SDOCorporation.
 * @author LinDongCheng <lindongcheng@snda.com>
 * @version $Id: IbatisFilesWriterImpl.java,v 1.0 2010-6-27 下午05:26:06 lindc Exp
 *          $
 * @create 2010-6-27 下午05:26:06
 */

public class IbatisFilesWriterImpl implements IbatisFilesWriter {

	/**
	 * 系统日志输出句柄
	 */
	private Logger logger = Logger.getLogger(IbatisFilesWriterImpl.class);

	/**
	 * Velocity引擎
	 */
	private VelocityEngine engine = new VelocityEngine();

	/**
	 * velocity模板路径信息
	 */
	private VelocityTempletePathTO velocityPathTO = new VelocityTempletePathTO();

	/**
	 * 实体在velocity上下文中的名字
	 */
	private String entityName;

	/**
	 * 实体属性列表在velocity上下文中的名字
	 */
	private String propertyListName;

	/**
	 * 初始化构造服务器
	 */
	synchronized public void init() {
		try {
			// 初始化Velocity环境
			Velocity.init();

			// 初始化Velocity引擎
//			Properties properties = new Properties();
//			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, velocityPathTO.getVelocityLoaderPath());
//			engine.init(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mypay.ibatisbuild.writer.IbatisFilesWriter#outputAllFile(com.mypay
	 * .ibatisbuild.to.EntityMetaInfoTO)
	 */
	public void outputAllFile(EntityMetaInfoTO entityMetaInfoTO, List<EntityPropertyMetaInfoTO> tableColMetaInfoTOList, boolean generateImpl) {
		// 构造JAVA包目录
		File subPackageDir = new File(entityMetaInfoTO.getJavaPackagePath());
		if (!subPackageDir.isDirectory()) {
			subPackageDir.mkdirs();
		}

		// 构造TEST包目录
		File testPackageDir = new File(entityMetaInfoTO.getTestPackagePath());
		if (!testPackageDir.isDirectory()) {
			testPackageDir.mkdirs();
		}

		// 构造velocity上下文环境
		VelocityContext velocityContext = makeVelocityContext(entityMetaInfoTO, tableColMetaInfoTOList);

		// 输出包的JAVADOC文件
		outputFile(entityMetaInfoTO.getPackageHtmlPath(), velocityPathTO.getPackageHtmlVmName(), velocityContext);

		// 输出AbstractEO文件
		outputFile(entityMetaInfoTO.getEoPathAbstract(), velocityPathTO.getEntityClassVmNameAbstract(), velocityContext);

		// 输出AbstractSQL文件
		outputFile(entityMetaInfoTO.getEoSqlPathAbstract(), velocityPathTO.getEntitySqlVmNameAbstract(), velocityContext);

		// 输出AbstractDAOImpl文件
		outputFile(entityMetaInfoTO.getEoDAOImplPathAbstract(), velocityPathTO.getEntityDAOImplVmNameAbstract(), velocityContext);

		// 输出AbstractDAOTest文件
		outputFile(entityMetaInfoTO.getEoDAOTestPathAbstract(), velocityPathTO.getEntityDAOTestVmNameAbstract(), velocityContext);

		// 在需要的情况下生产实现类
		if (generateImpl) {
			// 输出EO文件
			outputFile(entityMetaInfoTO.getEoPath(), velocityPathTO.getEntityClassVmName(), velocityContext);

			// 输出DAO文件
			outputFile(entityMetaInfoTO.getEoDAOPath(), velocityPathTO.getEntityDAOVmName(), velocityContext);

			// 输出SQL文件
			outputFile(entityMetaInfoTO.getEoSqlPath(), velocityPathTO.getEntitySqlVmName(), velocityContext);

			// 输出DAOTest文件
			outputFile(entityMetaInfoTO.getEoDAOTestPath(), velocityPathTO.getEntityDAOTestVmName(), velocityContext);

			// 输出DAOImpl文件
			outputFile(entityMetaInfoTO.getEoDAOImplPath(), velocityPathTO.getEntityDAOImplVmName(), velocityContext);
		}
	}

	/**
	 * 构造Velocity上下文环境
	 * 
	 * @param entityMetaInfoTO
	 * @param tableColMetaInfoTOList
	 * @return
	 */
	private VelocityContext makeVelocityContext(EntityMetaInfoTO entityMetaInfoTO, List<EntityPropertyMetaInfoTO> tableColMetaInfoTOList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(entityName, entityMetaInfoTO);
		map.put(propertyListName, tableColMetaInfoTOList);
		VelocityContext context = new VelocityContext();
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
		return context;
	}

	/**
	 * 输出单个文件
	 * 
	 * @param eoClassPath
	 *            准备输出的目标文件
	 * @param vmName
	 *            模板文件名
	 * @param velocityContext
	 *            velocity上下文环境
	 */
	private void outputFile(String eoClassPath, String vmName, VelocityContext velocityContext) {
		FileWriter eoOut = null;
		try {
			eoOut = new FileWriter(new File(eoClassPath));
			String vmPath = "/META-INF/templete/"+vmName;
			System.out.println(vmPath);
			InputStream resourceAsStream = getClass().getResourceAsStream(vmPath);
			InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
			engine.evaluate(velocityContext,eoOut,"velocity", inputStreamReader);
//			template.merge(velocityContext, eoOut);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("使用Velocity引擎构造内容时发生异常:", e);
		} finally {
			closeFileWriter(eoOut);
		}
	}

	/**
	 * 关闭文件输出流
	 * 
	 * @param eoOut
	 */
	private void closeFileWriter(FileWriter eoOut) {
		if (eoOut != null) {
			try {
				eoOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public VelocityTempletePathTO getVelocityPathTO() {
		return velocityPathTO;
	}

	public void setVelocityPathTO(VelocityTempletePathTO velocityPathTO) {
		this.velocityPathTO = velocityPathTO;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getPropertyListName() {
		return propertyListName;
	}

	public void setPropertyListName(String propertyListName) {
		this.propertyListName = propertyListName;
	}

	public static void main(String[] args) {
		try {

			VelocityContext context = new VelocityContext();
			Writer arg1 = new FileWriter("c:/a.txt");
			String arg2 = "";
			InputStream resourceAsStream = IbatisFilesWriterImpl.class.getResourceAsStream("/META-INF/ac_ibatisbuild_service.xml");
			Reader arg3 = new InputStreamReader(resourceAsStream);
			Velocity.evaluate(context, arg1, arg2, arg3);
//			Velocity.getTemplate("/log4j.xml");
//			engine.evaluate(context, out, "velocity", strInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
