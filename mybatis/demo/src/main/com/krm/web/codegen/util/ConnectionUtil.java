package com.krm.web.codegen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.krm.web.codegen.GenCoreConstant;

/**
 * 数据库连接工具类 主要是创建连接
 * @author Parker
 *
 */
public class ConnectionUtil {

    private static Connection connection;
    private static String DB_DRIVER;
    private static String DB_URL;
    private static String DB_USER_NAME;
    private static String DB_PASSWORD;


    private static ThreadLocal<Connection> connectionTl = new ThreadLocal<Connection>();

    private ConnectionUtil() {

    }

    private static ConnectionUtil instance;

    public static Connection getConnect() {
    	return connectionTl.get();
    }
    
    public static Statement createStatement() {
        try {
            return connectionTl.get().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            throw new RuntimeException("创建 Statement 发生异常", e);
        }
    }

    public static Statement createThisStatement() {
        if (instance == null) {
            initThis();
        }
        try {
            return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            throw new RuntimeException("创建 Statement 发生异常", e);
        }
    }

    private static void initThis() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("创建 Connection 发生异常", e);
        }
    }

    public static void init(String deiver, String url, String username, String passwd) {
        try {
            Class.forName(deiver);
            connectionTl.set(DriverManager.getConnection(url, username, passwd));
        } catch (Exception e) {
            throw new RuntimeException("创建 Connection 发生异常", e);
        }
    }

    public static void close() {
        try {
            if (!connectionTl.get().isClosed())
                connectionTl.get().close();
        } catch (Exception e) {
            throw new RuntimeException("关闭 Connection 发生异常", e);
        }
    }

    public static void closeThis() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (Exception e) {
            throw new RuntimeException("关闭 Connection 发生异常", e);
        }
    }

    public static void setDB_DRIVER(String dB_DRIVER) {
        DB_DRIVER = dB_DRIVER;
    }

    public static void setDB_URL(String dB_URL) {
        DB_URL = dB_URL;
    }

    public static void setDB_USER_NAME(String dB_USER_NAME) {
        DB_USER_NAME = dB_USER_NAME;
    }

    public static void setDB_PASSWORD(String dB_PASSWORD) {
        DB_PASSWORD = dB_PASSWORD;
    }

    public static String getRealUrl(String dbType, String ip, String port, String dbName){
    	if(dbType.equals(GenCoreConstant.ORACLE_11G)){
    		return "jdbc:oracle:thin:@"+ ip +":"+ port + ":" + dbName;
    	}if(dbType.equals(GenCoreConstant.ORACLE_12C)){
    		return "jdbc:oracle:thin:@"+ ip +":"+ port + "/" + dbName;
    	}else if(dbType.equals(GenCoreConstant.DB2)){
    		return "jdbc:db2://"+ ip +":"+ port + "/" + dbName;
    	}else if(dbType.equals(GenCoreConstant.MYSQL)){
    		return "jdbc:mysql://"+ ip +":"+ port;
    	}
    	return null;
    }
}
