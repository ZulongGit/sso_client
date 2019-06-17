package com.krm.web.demphecha.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.demphecha.model.Demohxdata;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 数据核查表DAO层
 * 2018-08-23
 */
public interface DemohxdataMapper extends Mapper<Demohxdata>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<Demohxdata> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	Demohxdata queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<Demohxdata> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);
}