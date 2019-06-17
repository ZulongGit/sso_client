package com.krm.web.codegen.util.read.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.TableFieldConfig;
import com.krm.web.codegen.util.read.BaseReadTable;
import com.krm.web.codegen.util.read.IReadTable;

/**
 * Oracle数据库的实现类
 * @author Parker
 *
 */
public class ReadTableForOracleImpl extends BaseReadTable implements IReadTable {
	
	private static String SCHEMA_SQL = "SELECT USERNAME SCHEMA_NAME FROM all_users ORDER BY SCHEMA_NAME";
	
	private static String ALL_TABLE_SQL = "select a.table_name, b.comments TABLE_COMMENT from all_tables a "
			+ "left join all_tab_comments b on a.table_name = b.table_name and a.owner = b.owner WHERE a.owner = upper('%s')";

    private static String FIELDS_SQL = "select t.COLUMN_NAME dbName,c.COMMENTS remarks,DATA_TYPE fieldType,null isPrimary,DATA_PRECISION numeric_precision,DATA_LENGTH length,DATA_SCALE decimalPrecision,"
    		+ "DATA_DEFAULT defaultValue,COLUMN_ID sorts,NULLABLE isNullable from all_tab_columns t "
    		+ "left join all_col_comments c on t.OWNER = c.OWNER and t.TABLE_NAME = c.TABLE_NAME and t.COLUMN_NAME = c.COLUMN_NAME "
    		+ "where t.Table_Name=upper('%s') and t.owner = upper('%s') order by COLUMN_ID";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTableForOracleImpl.class);

    @Override
    public List<CommonEntity> getAllSchema() {
        try {
            return getAllSchema(SCHEMA_SQL);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("获取表格数据发生异常");
        }
    }

    @Override
    public List<CommonEntity> getAllTable(String dbName) {
        try {
            return getAllTable(dbName, ALL_TABLE_SQL);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("获取表格数据发生异常");
        }
    }

    @Override
    public List<TableFieldConfig> initField(String dbName, String tableName) {
    	try {
    		return initField(dbName, tableName, FIELDS_SQL);
    	} catch (Exception e) {
    		LOGGER.error(e.getMessage(), e);
    		throw new RuntimeException("获取表格数据发生异常");
    	}
    }
    
    @Override
    protected String handlerTableComment(String comment) {
        if (comment.contains(";")) {
            return comment.split(";")[0];
        }
        if (comment.startsWith("InnoDB free")) {
            return null;
        }
        return comment;
    }

}
