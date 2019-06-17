package com.krm.web.file.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.file.model.UploadFile;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 附件表DAO层
 * 2017-12-11
 */
public interface UploadFileMapper extends Mapper<UploadFile>{

	List<CommonEntity> queryPageInfo(Map<String, Object> params); 
	
	List<UploadFile> entityList(Map<String, Object> params); 

	UploadFile queryOne(Map<String, Object> params); 

	int deleteByParams(Map<String, Object> params);

	int deleteRepeted(Map<String, Object> params);
}
