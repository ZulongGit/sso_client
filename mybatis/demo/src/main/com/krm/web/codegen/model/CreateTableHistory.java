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
 * 建表记录javaBean
 * 2018-04-21
 */
@Table(name="gen_create_table_history")
public class CreateTableHistory extends BaseEntity<CreateTableHistory>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
    private String  proId;      //项目编码
    private String  tableName;      //表名
    private String  tableComments;      //表注释
    private String  fieldName;      //字段名称
    private String  fieldAlias;      //字段注释
    private String  isPrimary;      //是否为主键
    private String  fieldType;      //字段类型
    private Integer  fieldLength;      //长度
    private Integer  fieldDecimal;      //小数
    private String  fieldDefaultValue;      //默认值
    private String  fieldIsNull;      //允许空值
    private Integer  sort;      //排序
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<CreateTableHistory>	list;			//批量保存
	
	public CreateTableHistory() {
    	super.setModuleName("建表记录");
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
	 * 表名
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return tableName;
	}
	/**
	 * 表注释
	 * @param tableComments
	 */
	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}
	public String getTableComments() {
		return tableComments;
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
	 * 字段注释
	 * @param fieldAlias
	 */
	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}
	public String getFieldAlias() {
		return fieldAlias;
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
	 * 字段类型
	 * @param fieldType
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldType() {
		return fieldType;
	}
	/**
	 * 长度
	 * @param fieldLength
	 */
	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}
	public Integer getFieldLength() {
		return fieldLength;
	}
	/**
	 * 小数
	 * @param fieldDecimal
	 */
	public void setFieldDecimal(Integer fieldDecimal) {
		this.fieldDecimal = fieldDecimal;
	}
	public Integer getFieldDecimal() {
		return fieldDecimal;
	}
	/**
	 * 默认值
	 * @param fieldDefaultValue
	 */
	public void setFieldDefaultValue(String fieldDefaultValue) {
		this.fieldDefaultValue = fieldDefaultValue;
	}
	public String getFieldDefaultValue() {
		return fieldDefaultValue;
	}
	/**
	 * 允许空值
	 * @param fieldIsNull
	 */
	public void setFieldIsNull(String fieldIsNull) {
		this.fieldIsNull = fieldIsNull;
	}
	public String getFieldIsNull() {
		return fieldIsNull;
	}
	/**
	 * 排序
	 * @param sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSort() {
		return sort;
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
	 * 批量保存
	 * @param list
	 */
	public List<CreateTableHistory> getList() {
		return list;
	}
	public void setList(List<CreateTableHistory> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "建表记录： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"tableName\": \""+tableName+"\", \"tableComments\": \""+tableComments+"\", \"fieldName\": \""+fieldName+"\", \"fieldAlias\": \""+fieldAlias+"\", \"fieldType\": \""+fieldType+"\", \"fieldLength\": \""+fieldLength+"\", \"fieldDecimal\": \""+fieldDecimal+"\", \"fieldDefaultValue\": \""+fieldDefaultValue+"\", \"fieldIsNull\": \""+fieldIsNull+"\", \"sort\": \""+sort+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}