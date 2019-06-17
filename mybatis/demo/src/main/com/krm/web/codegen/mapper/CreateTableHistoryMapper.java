package com.krm.web.codegen.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.CreateTableHistory;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 建表记录DAO层
 * 2018-01-03
 */
public interface CreateTableHistoryMapper extends Mapper<CreateTableHistory>{

	List<CommonEntity> queryPageInfo(Map<String, Object> params); 
	
	List<CreateTableHistory> entityList(Map<String, Object> params); 

	CreateTableHistory queryOne(Map<String, Object> params); 

	int insertBatch(List<CreateTableHistory> list);
	
	int deleteByParams(Map<String, Object> params);
	
	List<CommonEntity> list(Map<String, Object> params); 
	
	int executeCreate(Map<String, Object> params);
	
	int updateByParams(Map<String, Object> params);
}
