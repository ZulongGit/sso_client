package com.krm.web.abnormal.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.abnormal.model.DemoAbnormalData;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 异常规则数据DAO层
 * 2018-08-22
 */
public interface DemoAbnormalDataMapper extends Mapper<DemoAbnormalData>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<DemoAbnormalData> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	DemoAbnormalData queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<DemoAbnormalData> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);
}