package com.krm.web.codegen.util.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.common.base.CommonEntity;
import com.krm.common.utils.StringUtil;
import com.krm.web.codegen.model.JdbcType;
import com.krm.web.codegen.model.TableConfig;
import com.krm.web.codegen.model.TableFieldConfig;
import com.krm.web.codegen.util.ConnectionUtil;
import com.krm.web.codegen.util.StringConvert;

/**
 * 读表基础类
 * @author Parker
 *
 */
public abstract class BaseReadTable {

    private static final Logger logger = LoggerFactory.getLogger(BaseReadTable.class);

    /**
     * 获取所有数据库名
     * @param sql
     * @return
     * @throws SQLException
     */
    public List<CommonEntity> getAllSchema(String sql) throws SQLException {
        Statement statement = null;
        List<CommonEntity> list = new ArrayList<CommonEntity>();
        try {
            ResultSet rs = ConnectionUtil.createStatement().executeQuery(String.format(sql));
            while (rs.next()) {
            	CommonEntity entity = new CommonEntity();
            	entity.put("label", rs.getString(1));
            	entity.put("value", rs.getString(1));
                list.add(entity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        return list;
    }

    /**
     * 获取所有表名
     * @param dbName
     * @param sql
     * @return
     * @throws Exception
     */
    protected List<CommonEntity> getAllTable(String dbName, String sql) throws Exception {
        Statement statement = null;
        List<CommonEntity> list = new ArrayList<CommonEntity>();
        try {
            ResultSet rs = ConnectionUtil.createStatement().executeQuery(String.format(sql, dbName));
            String dbTableName = null;
            String comment = null;
            while (rs.next()) {
                dbTableName = rs.getString(1);
                comment = rs.getString(2);
                if (StringUtils.isEmpty(dbTableName)) {
                    throw new RuntimeException("表不存在");
                } else {
                	CommonEntity entity = new CommonEntity();
                    if (StringUtils.isNotEmpty(comment)) {
                        entity.put("label", dbTableName + "【" +handlerTableComment(comment) + "】");
                    }else{
                    	entity.put("label", dbTableName);
                    }
                    entity.put("value", dbTableName.toLowerCase());
                    list.add(entity);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        return list;
    }

    protected TableConfig getOneTable(String dbName, String tableName, String sql) throws Exception {
        Statement statement = null;
        try {
            ResultSet rs = ConnectionUtil.createStatement().executeQuery(String.format(sql, tableName, dbName));
            String dbTableName = null;
            while (rs.next()) {
                dbTableName = rs.getString(1);
            }
            if (StringUtils.isEmpty(dbTableName)) {
                throw new RuntimeException("表不存在");
            } else {
            	TableConfig entity = new TableConfig();
                entity.setTableName(dbTableName);
                return entity;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
    }
    
    /**
     * 获取表结构
     * @param dbName
     * @param tableName
     * @param sql
     * @return
     * @throws Exception
     */
    protected List<TableFieldConfig> initField(String dbName, String tableName, String sql) throws Exception {
        List<TableFieldConfig> list = new ArrayList<TableFieldConfig>();
        Statement statement = null;
        try {
            ResultSet rs = ConnectionUtil.createStatement()
                    .executeQuery(String.format(sql, tableName, dbName));
            TableFieldConfig field;
            while (rs.next()) {
                field = new TableFieldConfig();
                field.setDbName(rs.getString("dbName"));
                field.setFieldName(StringConvert.underlineToCamelhump(field.getDbName().toLowerCase()));
                field.setRemarks(rs.getString("remarks"));
                field.setIsPrimary(StringUtil.isEmpty(rs.getString("isPrimary")) ? "N" : "Y");
                if (StringUtils.isNotEmpty(rs.getString("length"))) {
                	field.setLength(rs.getInt("length"));
                } else {
                	field.setLength(rs.getInt("numeric_precision"));
                }
                field.setDecimalPrecision(rs.getInt("decimalPrecision"));
                field.setFieldType(rs.getString("fieldType"));
                field.setJavaType(getJavaType(field.getFieldType(), field.getLength(), field.getDecimalPrecision()));
                field.setDefaultValue(rs.getString("defaultValue"));
                field.setSorts(rs.getInt("sorts"));
                if("YES".equalsIgnoreCase(rs.getString("isNullable")) || "Y".equalsIgnoreCase(rs.getString("isNullable"))){
                	field.setIsNullable("N");
                }else{
                	field.setIsNullable("Y");
                }
                field.setIsListShow("Y");
                field.setIsUse("Y");
                field.setIsReadonly("N");
                list.add(field);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        return list;
    }
    
    /**
     * 获取java类型
     * @param fieldType 数据库类型
     * @param length	长度
     * @param decimalPrecision	小数精度
     * @return
     */
    protected String getJavaType(String fieldType, Integer length, Integer decimalPrecision){
    	if(fieldType.contains("(")){
    		fieldType = fieldType.substring(0, fieldType.indexOf("("));
    	}
    	Class<?> clazz = JdbcType.getCodeLookup().get(fieldType.toUpperCase());
        if(StringUtil.isNotEmpty(clazz) && clazz.equals(Number.class)){
        	if(decimalPrecision == 0){
        		if(length != null){
        			if(length < 6){
        				return "Integer";
        			}else{
        				return "Long";
        			}
        		}
        	}
			return "Double";
        }else{
        	return StringUtil.isEmpty(clazz) ? null : clazz.getSimpleName();
        }
    }
    protected abstract String handlerTableComment(String comment);
}
