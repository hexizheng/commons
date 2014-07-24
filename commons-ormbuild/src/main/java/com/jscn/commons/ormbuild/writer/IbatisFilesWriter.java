package com.jscn.commons.ormbuild.writer;

import java.util.List;

import com.jscn.commons.ormbuild.service.EntityMetaInfoTO;
import com.jscn.commons.ormbuild.service.EntityPropertyMetaInfoTO;

/**
 * 件生成器
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		LinDongCheng <lindongcheng@snda.com>
 * @version		$Id: IbatisFilesWriter.java,v 1.0 2010-6-27 下午05:37:17 lindc Exp $
 * @create		2010-6-27 下午05:37:17
 */
public interface IbatisFilesWriter {

	/**
	 * 输出所有文件
	 * @param entityMetaInfoTO
	 * @param tableColMetaInfoTOList
	 * @param onlyAbstract TODO
	 */
	void outputAllFile(EntityMetaInfoTO entityMetaInfoTO,List<EntityPropertyMetaInfoTO> tableColMetaInfoTOList, boolean onlyAbstract);

}