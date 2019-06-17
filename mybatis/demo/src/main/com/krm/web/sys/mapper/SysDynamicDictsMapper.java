package com.krm.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.sys.model.SysDynamicDicts;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 动态数据字典DAO层
 * 2017-11-30
 */
public interface SysDynamicDictsMapper extends Mapper<SysDynamicDicts>{

	List<CommonEntity> queryPageInfo(Map<String, Object> params); 
	
	List<SysDynamicDicts> entityList(Map<String, Object> params); 

	SysDynamicDicts queryOne(Map<String, Object> params); 

	public List<CommonEntity> exeuteDynamicSql(Map<String, Object> params);

	List<CommonEntity> allTable(Map<String, Object> params);

	List<CommonEntity> selectFields(Map<String, Object> params);
}
