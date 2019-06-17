package com.krm.web.codegen.util.read;

import java.util.List;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.TableFieldConfig;

/**
 * 读取数据库表,获取数据库表的属性
 * @author Parker
 *
 */
public interface IReadTable {
	/**
	 * 查询所有的库/架构
	 */
	public List<CommonEntity> getAllSchema();
	
	/**
	 * 查询所有的表
	 */
	public List<CommonEntity> getAllTable(String dbName);
	
    /**
     * 读取数据库表格属性
     */
    public List<TableFieldConfig> initField(String dbName, String tableName);


}
