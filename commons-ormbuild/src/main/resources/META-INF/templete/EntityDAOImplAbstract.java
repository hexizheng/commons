/**
 * 
 */
package $entityMetaInfoTO.packageName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.OptimisticLockingFailureException;

import com.jscn.commons.core.ibatis.IbatisBaseDAOImpl;

/**
 * ${entityMetaInfoTO.eoCommen}访问接口抽象实现
 * @description	
 * @usage		
 * @copyright	Copyright 2010 SDO Corporation. All rights reserved.
 * @company		SDOCorporation.
 * @author		${entityMetaInfoTO.createrName} <${entityMetaInfoTO.createEmail}>
 * @version		$Id: ${entityMetaInfoTO.eoDAOImplFileAbstract},v 1.0 ${entityMetaInfoTO.createTime} ${entityMetaInfoTO.createrName} Exp $
 * @create		${entityMetaInfoTO.createTime}
 */
public abstract class ${entityMetaInfoTO.eoDAOImplNameAbstract} extends IbatisBaseDAOImpl implements ${entityMetaInfoTO.eoDAOName} {

	
	@Override
	public Long insert(${entityMetaInfoTO.eoName} eo) {
		if(eo.${entityMetaInfoTO.pkGetterName}()!=null){
			insert("${entityMetaInfoTO.eoDAONameAbstract}.insertHasId", eo);
			return eo.${entityMetaInfoTO.pkGetterName}();
		}else{
			Long ppId = (Long) insert("${entityMetaInfoTO.eoDAONameAbstract}.insert", eo);
			eo.${entityMetaInfoTO.pkSetterName}(ppId);
			return ppId;
		}
	}

	
	@Override
	public ${entityMetaInfoTO.eoName} select(Long id) {
		return select(id,false);
	}
	
	@Override
	public ${entityMetaInfoTO.eoName} selectAndLock(Long id) {
		return select(id,true);
	}
	
	public ${entityMetaInfoTO.eoName} select(Long id, boolean isLock) {
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("id", id);
		paramMap.put("isLock", isLock);
		return (${entityMetaInfoTO.eoName}) queryForObject("${entityMetaInfoTO.eoDAONameAbstract}.select", paramMap);
	}
	
	
	
	@Override
	public ${entityMetaInfoTO.eoName} selectEOByEO(${entityMetaInfoTO.eoName} eo) {
		return (${entityMetaInfoTO.eoName}) queryForObject("${entityMetaInfoTO.eoDAONameAbstract}.selectEOByEO", eo);
	}
	
	@Override
	public int countByEO(${entityMetaInfoTO.eoName} eo) {
		Integer count = (Integer)queryForObject("${entityMetaInfoTO.eoDAONameAbstract}.countByEO", eo);
		return count!=null?count:0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<${entityMetaInfoTO.eoName}> selectListByEO(${entityMetaInfoTO.eoName} eo) {
		return queryForList("${entityMetaInfoTO.eoDAONameAbstract}.selectEOByEO", eo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<${entityMetaInfoTO.eoName}> selectListByEO(${entityMetaInfoTO.eoName} eo,int pageNO,int pageSize) {
		return queryForList("${entityMetaInfoTO.eoDAONameAbstract}.selectEOByEO", eo, (pageNO-1)*pageSize, pageSize);
	}

	
	@Override
	public int update(${entityMetaInfoTO.eoName} eo) {
		int update = update("${entityMetaInfoTO.eoDAONameAbstract}.update", eo);
		if (update == 0) {
			throw new OptimisticLockingFailureException("更新纪录[" + eo + "]时发生乐观锁并发异常");
		}
		eo.${entityMetaInfoTO.versionSetterMethodName}(eo.${entityMetaInfoTO.versionGetterMethodName}()+1);
		return update;
	}
	
	@Override
	public int updateByField(${entityMetaInfoTO.eoName} eo) {
		int update = update("${entityMetaInfoTO.eoDAONameAbstract}.updateByField", eo);
		if (update == 0) {
			throw new OptimisticLockingFailureException("更新纪录[" + eo + "]时发生乐观锁并发异常");
		}
		eo.${entityMetaInfoTO.versionSetterMethodName}(eo.${entityMetaInfoTO.versionGetterMethodName}()+1);
		return update;
	}

	@Override
	public int delete(${entityMetaInfoTO.eoName} eo) {
		int delete = delete("${entityMetaInfoTO.eoDAONameAbstract}.delete", eo);
		if (delete == 0) {
			throw new OptimisticLockingFailureException("删除纪录[" + eo + "]时发生乐观锁并发异常");
		}
		return delete;
	}

}
