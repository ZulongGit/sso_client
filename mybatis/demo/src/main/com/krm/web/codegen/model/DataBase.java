package com.krm.web.codegen.model;

public enum DataBase {
	
	ORACLE("oracle.jdbc.driver.OracleDriver"),
	DB2("com.ibm.db2.jcc.DB2Driver"),
	MYSQL("com.mysql.jdbc.Driver");
	
	private String value;  
    
	private DataBase(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
