package com.krm.web.codegen.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.TableConfig;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 代码生成表配置信息DAO层
 * 2018-03-30
 */
public interface TableConfigMapper extends Mapper<TableConfig>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<TableConfig> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	TableConfig queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<TableConfig> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);
	/**
	 * 根据项目、链接、数据库、表删除配置
	 */
	int deleteConfig(Map<String, Object> params);
	
}
