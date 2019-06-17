package com.krm.web.codegen.mapper;

import java.util.List;
import java.util.Map;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.TableFieldConfig;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Parker
 * 代码生成表字段配置信息DAO层
 * 2018-03-30
 */
public interface TableFieldConfigMapper extends Mapper<TableFieldConfig>{

	/**
	 * 列表查询,返回的是通用实体，不受实体属性限制，相当于map
	 */
	List<CommonEntity> queryPageInfo(Map<String, Object> params);
	/**
	 * 列表查询,返回的是实体
	 */
	List<TableFieldConfig> entityList(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是实体
	 */
	TableFieldConfig queryOne(Map<String, Object> params);
	/**
	 * 查询单条数据,返回的是通用实体，不受实体属性限制，相当于map
	 */
	CommonEntity queryOneCommon(Map<String, Object> params);
	/**
	 * 批量插入数据
	 */
	int insertBatch(List<TableFieldConfig> list);
	/**
	 * 根据不同条件删除数据，条件可组合
	 */
	int deleteByParams(Map<String, Object> params);
	/**
	 * 检查是否已经保存配置
	 */
	Map<String, Object> checkConfigs(Map<String, Object> params);
	/**
	 * 查询单表配置
	 */
	List<CommonEntity> selectConfigs(Map<String, Object> params);
}
