package com.krm.web.codegen.util.read;

import com.krm.web.codegen.model.DataBase;
import com.krm.web.codegen.util.read.impl.ReadTableForDb2Impl;
import com.krm.web.codegen.util.read.impl.ReadTableForMysqlImpl;
import com.krm.web.codegen.util.read.impl.ReadTableForOracleImpl;
import com.sun.star.uno.RuntimeException;

/**
 * 读取库的工厂
 * @author Parker
 *
 */
public class ReadTableFactory {

    public static IReadTable getReadTable(String dbType) {
        if ("mysql".equalsIgnoreCase(dbType)) {
            return new ReadTableForMysqlImpl();
        }else if ("oracle11".equalsIgnoreCase(dbType)) {
            return new ReadTableForOracleImpl();
        }else if ("oracle12".equalsIgnoreCase(dbType)) {
            return new ReadTableForOracleImpl();
        }else if ("db2".equalsIgnoreCase(dbType)) {
            return new ReadTableForDb2Impl();
        }
        throw new RuntimeException("数据库不支持");
    }

    public static String getDeiver(String dbType) {
        if ("mysql".equalsIgnoreCase(dbType)) {
            return DataBase.MYSQL.getValue();
        }else if ("oracle11".equalsIgnoreCase(dbType)) {
            return DataBase.ORACLE.getValue();
        }else if ("oracle12".equalsIgnoreCase(dbType)) {
            return DataBase.ORACLE.getValue();
        }else if ("db2".equalsIgnoreCase(dbType)) {
        	return DataBase.DB2.getValue();
        }
        throw new RuntimeException("数据库不支持");
    }
}
