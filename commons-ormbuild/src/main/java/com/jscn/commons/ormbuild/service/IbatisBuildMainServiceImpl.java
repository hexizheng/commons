/**
 * 
 */
package com.jscn.commons.ormbuild.service;

import java.util.ArrayList;
import java.util.List;

import com.jscn.commons.ormbuild.db.TableColMetaInfoEO;
import com.jscn.commons.ormbuild.db.TableMetaInfoDAO;
import com.jscn.commons.ormbuild.db.TableMetaInfoEO;
import com.jscn.commons.ormbuild.writer.IbatisFilesWriter;

/**
 * s生成器主服务默认实现
 * @description
 * @usage
 * @copyright Copyright 2010 SDO Corporation. All rights reserved.
 * @company SDOCorporation.
 * @author LinDongCheng <lindongcheng@snda.com>
 * @version $Id: IbatisBuildMainServiceImpl.java,v 1.0 2009-11-15 上午10:12:23
 *          lindc Exp $
 * @create 2009-11-15 上午10:12:23
 */
public class IbatisBuildMainServiceImpl implements IbatisBuildMainService {

	/**
	 * 数据库访问接口
	 */
	private TableMetaInfoDAO tableMetaInfoDAO;
	
	/**
	 * Ibatis映射文件生成器
	 */
	private IbatisFilesWriter ibatisFilesWriter;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mypay.ibatisbuild.main.IbatisBuildMainService#buildIbatisMapping(
	 * java.lang.String)
	 */
	public void buildIbatisMapping(String[] tableNames,String tableNamePattern, boolean onlyAbstract, EntityCreateInfoTO eciTO) {
		//查询所有符合要求的表信息并循环进行处理
		List<TableMetaInfoEO> tableMetaInfoEOList = tableMetaInfoDAO.findTableMetaInfoByTableNamePattern(tableNames, tableNamePattern);
		for (TableMetaInfoEO tableMetaInfoEO : tableMetaInfoEOList) {
			buildEntityFiles(tableMetaInfoEO,eciTO, onlyAbstract);
		}
	}

	/**
	 * 输出单个表的所有文件
	 * @param tableMetaInfoEO
	 * @param generateImpl TODO
	 */
	private void buildEntityFiles(TableMetaInfoEO tableMetaInfoEO,EntityCreateInfoTO eciTO, boolean generateImpl) {
		// 构造实体对象元信息
		List<TableColMetaInfoEO> tableColMetaInfoEOList = tableMetaInfoDAO.findTableColMetaInfoByTableName(tableMetaInfoEO.getTableName());
		String aColumnName = tableColMetaInfoEOList.get(0).getColumnName();//任意取得表的一个列名称(目的是取得表的简称)
		EntityMetaInfoTO entityMetaInfoTO = new EntityMetaInfoTO(tableMetaInfoEO,eciTO,aColumnName);

		// 将新属性信息添加到列表中
		List<EntityPropertyMetaInfoTO> tableColMetaInfoTOList = new ArrayList<EntityPropertyMetaInfoTO>();
		for (TableColMetaInfoEO tableColMetaInfoEO : tableColMetaInfoEOList) {
			tableColMetaInfoTOList.add(new EntityPropertyMetaInfoTO(entityMetaInfoTO,tableColMetaInfoEO,eciTO));
		}

		//输出当前实体的所有文件
		ibatisFilesWriter.outputAllFile(entityMetaInfoTO, tableColMetaInfoTOList, generateImpl);
	}

	public TableMetaInfoDAO getTableMetaInfoDAO() {
		return tableMetaInfoDAO;
	}

	public void setTableMetaInfoDAO(TableMetaInfoDAO tableMetaInfoDAO) {
		this.tableMetaInfoDAO = tableMetaInfoDAO;
	}

	public IbatisFilesWriter getIbatisFilesWriter() {
		return ibatisFilesWriter;
	}

	public void setIbatisFilesWriter(IbatisFilesWriter ibatisFilesWriter) {
		this.ibatisFilesWriter = ibatisFilesWriter;
	}
}
