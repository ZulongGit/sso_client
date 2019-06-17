package com.krm.web.codegen.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;
import com.krm.common.base.BaseFile;

/**
 * 
 * @author Parker
 * 代码生成表字段配置信息javaBean
 * 2018-04-11
 */
@Table(name="gen_table_field_config")
public class TableFieldConfig extends BaseEntity<TableFieldConfig>{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
	private	String	tableId;			//表配置编码
	private	String	fieldName;			//字段名称
	private	String	dbName;			//字段数据库名称
	private	String	remarks;			//中文名称
	private	String	isPrimary = "N";			//是否为主键
	private	String	fieldType;			//字段数据库类型
	private	String	javaType;			//字段java类型
	private	Integer	length;			//字段长度
	private	Integer	decimalPrecision;			//小数精度
	private	String	defaultValue;			//默认值
	private	Integer	sorts;			//排序
	private	String	formType;			//表单类型
	private	String	dictType;			//字典类型
	private	String	isNullable = "N";			//是否必填项
	private	String	isReadonly = "N";			//是否readonly
	private	String	isListShow = "N";			//列表是否显示
	private	String	isUse = "N";			//字段是否使用
	private	String	queryMode;			//SQL查询模式
	private	String	isImOutport = "N";			//是否为导入导出字段
	private	Integer	minValue;			//最小值
	private	Integer	maxValue;			//最大值
	private	String	createBy;			//创建人
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//最近修改人
	private	Date	updateDate;			//最近修改时间
	private	String	delFlag;			//逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public TableFieldConfig() {
    	super.setModuleName("代码生成表字段配置信息功能");
	}

	/**
	 * @param isPrimary
	 * @param isNullable
	 * @param isReadonly
	 * @param isListShow
	 * @param isUse
	 * @param isImOutport
	 */
	public TableFieldConfig(String isPrimary, String isNullable, String isReadonly, String isListShow, String isUse,
			String isImOutport) {
		super();
		this.isPrimary = isPrimary;
		this.isNullable = isNullable;
		this.isReadonly = isReadonly;
		this.isListShow = isListShow;
		this.isUse = isUse;
		this.isImOutport = isImOutport;
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
	 * 表配置编码
	 * @param tableId
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableId() {
		return tableId;
	}

	/**
	 * 字段名称
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 字段数据库名称
	 * @param dbName
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbName() {
		return dbName;
	}

	/**
	 * 中文名称
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 是否为主键
	 * @param isPrimary
	 */
	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}
	public String getIsPrimary() {
		return isPrimary;
	}

	/**
	 * 字段数据库类型
	 * @param fieldType
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * 字段java类型
	 * @param javaType
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getJavaType() {
		return javaType;
	}

	/**
	 * 字段长度
	 * @param length
	 */
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getLength() {
		return length;
	}

	/**
	 * 小数精度
	 * @param decimalPrecision
	 */
	public void setDecimalPrecision(Integer decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}
	public Integer getDecimalPrecision() {
		return decimalPrecision;
	}

	/**
	 * 默认值
	 * @param defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 排序
	 * @param sorts
	 */
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}
	public Integer getSorts() {
		return sorts;
	}

	/**
	 * 表单类型
	 * @param formType
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getFormType() {
		return formType;
	}

	/**
	 * 字典类型
	 * @param dictType
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictType() {
		return dictType;
	}

	/**
	 * 是否必填项
	 * @param isNullable
	 */
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	public String getIsNullable() {
		return isNullable;
	}

	/**
	 * 是否readonly
	 * @param isReadonly
	 */
	public void setIsReadonly(String isReadonly) {
		this.isReadonly = isReadonly;
	}
	public String getIsReadonly() {
		return isReadonly;
	}

	/**
	 * 列表是否显示
	 * @param isListShow
	 */
	public void setIsListShow(String isListShow) {
		this.isListShow = isListShow;
	}
	public String getIsListShow() {
		return isListShow;
	}

	/**
	 * 字段是否使用
	 * @param isUse
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getIsUse() {
		return isUse;
	}

	/**
	 * SQL查询模式
	 * @param queryMode
	 */
	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}
	public String getQueryMode() {
		return queryMode;
	}

	/**
	 * 是否为导入导出字段
	 * @param isImOutport
	 */
	public void setIsImOutport(String isImOutport) {
		this.isImOutport = isImOutport;
	}
	public String getIsImOutport() {
		return isImOutport;
	}

	/**
	 * 最小值
	 * @param minValue
	 */
	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}
	public Integer getMinValue() {
		return minValue;
	}

	/**
	 * 最大值
	 * @param maxValue
	 */
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	public Integer getMaxValue() {
		return maxValue;
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
	 * 批量上传文件list
	 * @param upfileList
	 */
	public List<BaseFile> getUpfileList() {
		return upfileList;
	}
	public void setUpfileList(List<BaseFile> upfileList) {
		this.upfileList = upfileList;
	}

	@Override
	public String toString() {
		return "代码生成表字段配置信息： {\"id\": \""+id+"\", \"tableId\": \""+tableId+"\", \"fieldName\": \""+fieldName+"\", \"dbName\": \""+dbName+"\", \"remarks\": \""+remarks+"\", \"isPrimary\": \""+isPrimary+"\", \"fieldType\": \""+fieldType+"\", \"javaType\": \""+javaType+"\", \"length\": \""+length+"\", \"decimalPrecision\": \""+decimalPrecision+"\", \"defaultValue\": \""+defaultValue+"\", \"sorts\": \""+sorts+"\", \"formType\": \""+formType+"\", \"dictType\": \""+dictType+"\", \"isNullable\": \""+isNullable+"\", \"isReadonly\": \""+isReadonly+"\", \"isListShow\": \""+isListShow+"\", \"isUse\": \""+isUse+"\", \"queryMode\": \""+queryMode+"\", \"isImOutport\": \""+isImOutport+"\", \"minValue\": \""+minValue+"\", \"maxValue\": \""+maxValue+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}