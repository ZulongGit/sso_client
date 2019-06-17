package com.krm.web.codegen.model;

import java.io.Serializable;
import java.util.List;

import com.krm.common.base.CommonEntity;

public class CodeGenModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CommonParams params;
	private TableConfig tableConfig;
	private List<CommonEntity> fieldConfigs;
	private List<String> files;
	
	public CommonParams getParams() {
		return params;
	}
	public TableConfig getTableConfig() {
		return tableConfig;
	}
	public List<CommonEntity> getFieldConfigs() {
		return fieldConfigs;
	}
	public List<String> getFiles() {
		return files;
	}
	
	public void setParams(CommonParams params) {
		this.params = params;
	}
	public void setTableConfig(TableConfig tableConfig) {
		this.tableConfig = tableConfig;
	}
	public void setFieldConfigs(List<CommonEntity> fieldConfigs) {
		this.fieldConfigs = fieldConfigs;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
}
