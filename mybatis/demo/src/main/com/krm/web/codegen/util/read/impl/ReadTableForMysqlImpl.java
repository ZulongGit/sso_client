package com.krm.web.codegen.util.read.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.common.base.CommonEntity;
import com.krm.web.codegen.model.TableFieldConfig;
import com.krm.web.codegen.util.read.BaseReadTable;
import com.krm.web.codegen.util.read.IReadTable;

/**
 * MySql数据库的实现类
 * @author Parker
 *
 */
public class ReadTableForMysqlImpl extends BaseReadTable implements IReadTable {

	private static String SCHEMA_SQL = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME != 'information_schema' AND SCHEMA_NAME !=  'mysql' ORDER BY SCHEMA_NAME";
	
	private static String ALL_TABLE_SQL = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = '%s'";
	
    private static String FIELDS_SQL = "SELECT column_name dbName, column_comment remarks, column_key isPrimary, data_type fieldType, numeric_precision, character_maximum_length length,"
    								 + " numeric_scale decimalPrecision, column_default defaultValue, ordinal_position sorts, is_nullable isNullable from information_schema.columns "
    								 + "where table_name = '%s' and table_schema = '%s' order by ordinal_position";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTableForDb2Impl.class);


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
