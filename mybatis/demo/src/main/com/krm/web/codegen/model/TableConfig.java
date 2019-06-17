package com.krm.web.codegen.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;

/**
 * 
 * @author Parker
 * 代码生成表配置信息javaBean
 * 2018-04-11
 */
@Table(name="gen_table_config")
public class TableConfig extends BaseEntity<TableConfig>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
    private String  proId;      //项目编码
    private String  dbId;      //数据库链接编码
    private String  schemaName;      //数据库名称
    private String  tableName;      //表名称
    private String  className;      //基础类名
    private String  remarks;      //功能名称
    private String  moduleName;      //模块名称
    private String  isImport;      //是否导入
    private String  isExport;      //是否到出
    private String  isUploadFile;      //是否上传附件
    private String  userIdFieldBind;      //用户字段绑定
    private String  organCodeFieldBind;      //机构号字段绑定
    private String  isPage;      //是否分页
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
    @Transient
	private List<TableFieldConfig> configs;	
	
	public TableConfig() {
    	super.setModuleName("代码生成表配置信息");
	}
	/**
	 * 主键
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	/**
	 * 项目编码
	 * @param proId
	 */
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getProId() {
		return proId;
	}
	/**
	 * 数据库链接编码
	 * @param dbId
	 */
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public String getDbId() {
		return dbId;
	}
	/**
	 * 数据库名称
	 * @param schemaName
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getSchemaName() {
		return schemaName;
	}
	/**
	 * 表名称
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return tableName;
	}
	/**
	 * 基础类名
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassName() {
		return className;
	}
	/**
	 * 功能名称
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 模块名称
	 * @param moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * 是否导入
	 * @param isImport
	 */
	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}
	public String getIsImport() {
		return isImport;
	}
	/**
	 * 是否到出
	 * @param isExport
	 */
	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}
	public String getIsExport() {
		return isExport;
	}
	/**
	 * 是否上传附件
	 * @param isUploadFile
	 */
	public void setIsUploadFile(String isUploadFile) {
		this.isUploadFile = isUploadFile;
	}
	public String getIsUploadFile() {
		return isUploadFile;
	}
	/**
	 * 用户字段绑定
	 * @param userIdFieldBind
	 */
	public void setUserIdFieldBind(String userIdFieldBind) {
		this.userIdFieldBind = userIdFieldBind;
	}
	public String getUserIdFieldBind() {
		return userIdFieldBind;
	}
	/**
	 * 机构号字段绑定
	 * @param organCodeFieldBind
	 */
	public void setOrganCodeFieldBind(String organCodeFieldBind) {
		this.organCodeFieldBind = organCodeFieldBind;
	}
	public String getOrganCodeFieldBind() {
		return organCodeFieldBind;
	}
	/**
	 * 是否分页
	 * @param isPage
	 */
	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}
	public String getIsPage() {
		return isPage;
	}
	/**
	 * 创建人
	 * @param createBy
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 最近修改人
	 * @param updateBy
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 最近修改时间
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 逻辑删除标记(0.正常，1.删除)
	 * @param delFlag
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * 字段配置
	 * @param upfileList
	 */
	public List<TableFieldConfig> getConfigs() {
		return configs;
	}
	public void setConfigs(List<TableFieldConfig> configs) {
		this.configs = configs;
	}
	@Override
	public String toString() {
		return "代码生成表配置信息： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"dbId\": \""+dbId+"\", \"schemaName\": \""+schemaName+"\", \"tableName\": \""+tableName+"\", \"className\": \""+className+"\", \"remarks\": \""+remarks+"\", \"moduleName\": \""+moduleName+"\", \"isImport\": \""+isImport+"\", \"isExport\": \""+isExport+"\", \"isUploadFile\": \""+isUploadFile+"\", \"userIdFieldBind\": \""+userIdFieldBind+"\", \"organCodeFieldBind\": \""+organCodeFieldBind+"\", \"isPage\": \""+isPage+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}