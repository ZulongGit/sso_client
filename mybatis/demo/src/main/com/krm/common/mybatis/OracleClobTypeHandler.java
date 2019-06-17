package com.krm.common.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.CLOB;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class OracleClobTypeHandler implements TypeHandler<Object> {
	
	public Object valueOf(String param) {
		return null;
	}

	@Override
	public Object getResult(ResultSet arg0, String arg1) throws SQLException {
		CLOB clob = null;
		if(arg0.getClob(arg1) instanceof com.alibaba.druid.proxy.jdbc.ClobProxyImpl){
          com.alibaba.druid.proxy.jdbc.ClobProxyImpl impl = (com.alibaba.druid.proxy.jdbc.ClobProxyImpl)arg0.getClob(arg1);
          clob = (CLOB) impl.getRawClob(); // 获取原生的这个 Clob
        }else{
        	clob = (CLOB) arg0.getClob(arg1);
        }
		
		return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
	}

	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Object arg2, JdbcType arg3) throws SQLException {
		CLOB clob = CLOB.empty_lob();
		clob.setString(1, (String) arg2);
		arg0.setClob(arg1, clob);
	}
}
