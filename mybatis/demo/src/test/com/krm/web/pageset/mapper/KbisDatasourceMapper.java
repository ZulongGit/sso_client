package com.krm.web.pageset.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.pageset.model.KbisDatasource;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author fanxiaofeng
 * 数据源DAO层
 * 2018-10-12
 */
public interface KbisDatasourceMapper extends Mapper<KbisDatasource>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<KbisDatasource> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	KbisDatasource queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<KbisDatasource> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);
}