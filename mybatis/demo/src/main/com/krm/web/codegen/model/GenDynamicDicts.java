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
 * 项目非固定字典配置javaBean
 * 2018-05-30
 */
@Table(name="gen_dynamic_dicts")
public class GenDynamicDicts extends BaseEntity<GenDynamicDicts>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
    private String  proId;      //所属项目
    private String  keyName;      //文本属性字段
    private String  valueName;      //值属性字段
    @Transient
	private	String	keyName1;			//文本属性名1
	@Transient
	private	String	valueName1;			//值属性名1
    private String  sqlContent;      //SQL内容
    private String  type;      //类型
    private String  remarks;      //说明
    private String  dataScope;      //数据范围
    private String  tableName;      //关联表
    private String  sqlMode;      //模式
    private String  userIdFieldBind;      //用户字段绑定
    private String  organCodeFieldBind;      //机构号字段绑定
    @Transient
    private String  userIdFieldBind1;      //用户字段绑定1
    @Transient
    private String  organCodeFieldBind1;      //机构号字段绑定1
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public GenDynamicDicts() {
    	super.setModuleName("项目非固定字典配置");
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
	 * 所属项目
	 * @param proId
	 */
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getProId() {
		return proId;
	}
	/**
	 * 文本属性字段
	 * @param keyName
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyName() {
		return keyName;
	}
	/**
	 * 值属性字段
	 * @param valueName
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getValueName() {
		return valueName;
	}
	/**
	 * SQL内容
	 * @param sqlContent
	 */
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}
	public String getSqlContent() {
		return sqlContent;
	}
	/**
	 * 类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	/**
	 * 说明
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 数据范围
	 * @param dataScope
	 */
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}
	public String getDataScope() {
		return dataScope;
	}
	/**
	 * 关联表
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return tableName;
	}
	/**
	 * 模式
	 * @param sqlMode
	 */
	public void setSqlMode(String sqlMode) {
		this.sqlMode = sqlMode;
	}
	public String getSqlMode() {
		return sqlMode;
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
	
	

	public String getKeyName1() {
		return keyName1;
	}
	public String getValueName1() {
		return valueName1;
	}
	public String getUserIdFieldBind1() {
		return userIdFieldBind1;
	}
	public String getOrganCodeFieldBind1() {
		return organCodeFieldBind1;
	}
	public void setKeyName1(String keyName1) {
		this.keyName1 = keyName1;
	}
	public void setValueName1(String valueName1) {
		this.valueName1 = valueName1;
	}
	public void setUserIdFieldBind1(String userIdFieldBind1) {
		this.userIdFieldBind1 = userIdFieldBind1;
	}
	public void setOrganCodeFieldBind1(String organCodeFieldBind1) {
		this.organCodeFieldBind1 = organCodeFieldBind1;
	}
	@Override
	public String toString() {
		return "项目非固定字典配置： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"keyName\": \""+keyName+"\", \"valueName\": \""+valueName+"\", \"keyName1\": \""+keyName1+"\", \"valueName1\": \""+valueName1+ "\", \"sqlContent\": \""+sqlContent+"\", \"type\": \""+type+"\", \"remarks\": \""+remarks+"\", \"dataScope\": \""+dataScope+"\", \"tableName\": \""+tableName+"\", \"sqlMode\": \""+sqlMode+"\", \"userIdFieldBind\": \""+userIdFieldBind+"\", \"organCodeFieldBind\": \""+organCodeFieldBind+"\", \"userIdFieldBind1\": \""+userIdFieldBind1+"\", \"organCodeFieldBind1\": \""+organCodeFieldBind1+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}