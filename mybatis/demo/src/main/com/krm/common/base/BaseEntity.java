package com.krm.common.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
public class BaseEntity<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Transient
	private String userDataScope; 	//用户的数据范围
	@Transient
	private String moduleName;
	@Transient
	private String rowId;
	
	public BaseEntity() {
		super();
	}

	public String getUserDataScope() {
		return userDataScope;
	}

	public void setUserDataScope(String userDataScope) {
		this.userDataScope = userDataScope;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

}
