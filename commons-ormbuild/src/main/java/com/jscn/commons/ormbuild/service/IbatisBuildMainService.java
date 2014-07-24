package com.jscn.commons.ormbuild.service;


/**
 * 主服务
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: IbatisBuildMainService.java,v 1.0 2009-11-15 下午04:22:38 lindc Exp $
 * @create		2009-11-15 下午04:22:38
 */
public interface IbatisBuildMainService {

	/**
	 * 生成IBatis映射文件
	 * @param tableNames 表名称样式
	 * @param tableNamePattern TODO
	 * @param generateImpl 是否生产实现类(非抽象类)
	 */
	void buildIbatisMapping(String[] tableNames,String tableNamePattern, boolean generateImpl, EntityCreateInfoTO eciTO);

}