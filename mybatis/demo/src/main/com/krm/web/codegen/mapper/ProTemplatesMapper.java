package com.krm.web.codegen.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.ProTemplates;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 模板配置DAO层
 * 2018-04-09
 */
public interface ProTemplatesMapper extends Mapper<ProTemplates>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<ProTemplates> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	ProTemplates queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<ProTemplates> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);
	
	List<ProTemplates> getTemplateByIds(@Param("ids") String[] templates);
}
