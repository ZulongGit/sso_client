package com.krm.web.codegen.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.GenDict;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 枚举类数据字典DAO层
 * 2018-05-28
 */
public interface GenDictMapper extends Mapper<GenDict>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<GenDict> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	GenDict queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<GenDict> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);

	List<CommonEntity> getDicts(Map<String, Object> params);
}