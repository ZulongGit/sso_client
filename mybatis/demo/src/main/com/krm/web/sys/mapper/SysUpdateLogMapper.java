package com.krm.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.sys.model.SysUpdateLog;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 系统升级日志DAO层
 * 2017-12-27
 */
public interface SysUpdateLogMapper extends Mapper<SysUpdateLog>{

	List<CommonEntity> queryPageInfo(Map<String, Object> params); 
	
	List<SysUpdateLog> entityList(Map<String, Object> params); 

	SysUpdateLog queryOne(Map<String, Object> params); 

	int deleteByParams(Map<String, Object> params);

	List<CommonEntity> updateLogYear(Map<String, Object> params);
	
	List<CommonEntity> allUpdateLog(Map<String, Object> params);
}
