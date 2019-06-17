package com.krm.web.codegen.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum JdbcType {

	VARCHAR(Arrays.asList("VARCHAR", "VARCHAR2", "VARGRAPHIC", "TINYTEXT", "TEXT"), String.class),
	VARCHAR2(Arrays.asList("VARCHAR2", "NVARCHAR2"), String.class),
	LONGVARCHAR(Arrays.asList("LONG VARCHAR", "LONG VARGRAPHIC", "LONG"), String.class),
	CHAR(Arrays.asList("CHAR", "NCHAR", "GRAPHIC", "ENUM", "SET"), String.class),
	CLOB(Arrays.asList("CLOB", "NCLOB", "DBCLOB"), String.class),
	INTEGER(Arrays.asList("INTEGER", "INT", "MEDIUMINT"), Integer.class),
	SMALLINT(Arrays.asList("SMALLINT", "TINYINT"), Integer.class),
	BIGINT(Arrays.asList("BIGINT"), Long.class),
	NUMERIC(Arrays.asList("NUMERIC"), BigDecimal.class),
	DECIMAL(Arrays.asList("DECIMAL", "MONEY", "SMALLMONEY"), BigDecimal.class),
	NUMBER(Arrays.asList("NUMBER"), Number.class),
	DATE(Arrays.asList("DATE", "YEAR"), Date.class),
	TIME(Arrays.asList("TIME"), Date.class),
	TIMESTAMP(Arrays.asList("TIMESTAMP", "DATETIME", "SMALLDATETIME"), Date.class),
	REAL(Arrays.asList("REAL"), Float.class),
	FLOAT(Arrays.asList("FLOAT"), Double.class),
	DOUBLE(Arrays.asList("DOUBLE", "PRECISION"), Double.class),
	BIT(Arrays.asList("N/A", "BIT"), Double.class),
	BLOB(Arrays.asList("BLOB", "MEDIUMBLOB"), byte[].class),
	BINARY(Arrays.asList("CHAR FOR BIT DATA", "RAW", "BINARY", "IMAGE"), byte[].class),
	LONGVARBINARY(Arrays.asList("LONG VARCHAR FOR BIT DATA", "LONG RAW"), byte[].class),
	VARBINARY(Arrays.asList("VARCHAR FOR BIT DATA", "VARBINARY"), byte[].class);
	
	private List<String> list;
	private Class<?> clazz;
	private static Map<String, Class<?>> codeLookup;
	
	private JdbcType(List<String> list, Class<?> clazz) {
		this.list = list;
		this.clazz = clazz;
	}
	public List<String> getList() {
		return list;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public static Map<String, Class<?>> getCodeLookup() {
		return codeLookup;
	}


	static {
        JdbcType.codeLookup = new HashMap<String, Class<?>>();
        for (final JdbcType type : values()) {
			List<String> list = type.getList();
			for (String str : list) {
				JdbcType.codeLookup.put(str, type.getClazz());
			}
        }
    }
	
	public static void main(String[] args) {
		for (final JdbcType type : values()) {
			List<String> list = type.getList();
			for (String str : list) {
				JdbcType.codeLookup.put(str, type.getClazz());
			}
        }
		for (String str : JdbcType.codeLookup.keySet()) {
			System.out.println(str + "---->" +JdbcType.codeLookup.get(str).getSimpleName());
		}
	}
}
