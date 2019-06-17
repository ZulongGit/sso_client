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
 * 异常规则javaBean
 * 2018-08-22
 */
@Table(name="demo_abnormal_rule")
public class DemoAbnormalRule extends BaseEntity<DemoAbnormalRule>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="规则编码", filedAlign=1, sorts=1)
	@Id
    private String  ruleCode;      //规则编码
	@ExcelField(filedTitle="异常规则名称", filedAlign=1, sorts=2)
    private String  ruleName;      //异常规则名称
	@ExcelField(filedTitle="异常规则类型", filedAlign=1, sorts=3)
    private String  ruleType;      //异常规则类型
	@ExcelField(filedTitle="异常规则参数", filedAlign=1, sorts=4)
    private String  ruleParamter;      //异常规则参数
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=5)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=6)
    private String  createBy;      //创建人
	@ExcelField(filedTitle="描述", filedAlign=1, sorts=7)
    private String  description;      //描述
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DemoAbnormalRule() {
    	super.setModuleName("异常规则");
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
	 * 异常规则名称
	 * @param ruleName
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleName() {
		return ruleName;
	}
	/**
	 * 异常规则类型
	 * @param ruleType
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuleType() {
		return ruleType;
	}
	/**
	 * 异常规则参数
	 * @param ruleParamter
	 */
	public void setRuleParamter(String ruleParamter) {
		this.ruleParamter = ruleParamter;
	}
	public String getRuleParamter() {
		return ruleParamter;
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
		return "异常规则： {\"ruleCode\": \""+ruleCode+"\", \"ruleName\": \""+ruleName+"\", \"ruleType\": \""+ruleType+"\", \"ruleParamter\": \""+ruleParamter+"\", \"createDate\": \""+createDate+"\", \"createBy\": \""+createBy+"\", \"description\": \""+description+"\"}";
	}
}