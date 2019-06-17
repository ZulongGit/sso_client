package com.krm.web.abnormal.model;

import java.util.*;

import com.krm.common.base.BaseEntity;
import com.krm.common.base.BaseFile;

import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Table;
import com.krm.common.utils.excel.annotation.ExcelField;

/**
 * 
 * @author Parker
 * 异常规则数据javaBean
 * 2018-08-22
 */
@Table(name="demo_abnormal_data")
public class DemoAbnormalData extends BaseEntity<DemoAbnormalData>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="ID", filedAlign=1, sorts=1)
    private String  id;      //ID
	@ExcelField(filedTitle="对象名称", filedAlign=1, sorts=2)
    private String  objName;      //对象名称
	@ExcelField(filedTitle="对象标识", filedAlign=1, sorts=3)
    private String  objBs;      //对象标识
	@ExcelField(filedTitle="对象类型", filedAlign=1, sorts=4)
    private String  objType;      //对象类型
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=5)
    private String  createBy;      //创建人
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=6)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="修改时间", filedAlign=1, sorts=7)
    private Date  updateDate;      //修改时间
	@ExcelField(filedTitle="修改人", filedAlign=1, sorts=8)
    private String  updateBy;      //修改人
	@ExcelField(filedTitle="状态", filedAlign=1, sorts=9)
    private String  status;      //状态
	@ExcelField(filedTitle="是否忽略", filedAlign=1, sorts=10)
    private String  isIgnore;      //是否忽略
	@ExcelField(filedTitle="描述", filedAlign=1, sorts=11)
    private String  description;      //描述
	@ExcelField(filedTitle="反馈信息", filedAlign=1, sorts=12)
    private String  feedback;      //反馈信息
	@ExcelField(filedTitle="规则编码", filedAlign=1, sorts=13)
    private String  ruleCode;      //规则编码
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DemoAbnormalData() {
    	super.setModuleName("异常规则数据");
	}
	/**
	 * ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	/**
	 * 对象名称
	 * @param objName
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getObjName() {
		return objName;
	}
	/**
	 * 对象标识
	 * @param objBs
	 */
	public void setObjBs(String objBs) {
		this.objBs = objBs;
	}
	public String getObjBs() {
		return objBs;
	}
	/**
	 * 对象类型
	 * @param objType
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getObjType() {
		return objType;
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
	 * 修改时间
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 修改人
	 * @param updateBy
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 状态
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	/**
	 * 是否忽略
	 * @param isIgnore
	 */
	public void setIsIgnore(String isIgnore) {
		this.isIgnore = isIgnore;
	}
	public String getIsIgnore() {
		return isIgnore;
	}
	/**
	 * 描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	/**
	 * 反馈信息
	 * @param feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getFeedback() {
		return feedback;
	}
	/**
	 * 规则编码
	 * @param ruleCode
	 */
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getRuleCode() {
		return ruleCode;
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
		return "异常规则数据： {\"id\": \""+id+"\", \"objName\": \""+objName+"\", \"objBs\": \""+objBs+"\", \"objType\": \""+objType+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateDate\": \""+updateDate+"\", \"updateBy\": \""+updateBy+"\", \"status\": \""+status+"\", \"isIgnore\": \""+isIgnore+"\", \"description\": \""+description+"\", \"feedback\": \""+feedback+"\", \"ruleCode\": \""+ruleCode+"\"}";
	}
}