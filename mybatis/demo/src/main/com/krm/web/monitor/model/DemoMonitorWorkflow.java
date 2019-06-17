package com.krm.web.monitor.model;

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
 * 监控工作流配置javaBean
 * 2018-08-22
 */
@Table(name="demo_monitor_workflow")
public class DemoMonitorWorkflow extends BaseEntity<DemoMonitorWorkflow>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="流程编码", filedAlign=1, sorts=1)
	@Id
    private String  wfCode;      //流程编码
	@ExcelField(filedTitle="流程名称", filedAlign=1, sorts=2)
    private String  wfName;      //流程名称
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=3)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=4)
    private String  createBy;      //创建人
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DemoMonitorWorkflow() {
    	super.setModuleName("监控工作流配置");
	}
	/**
	 * 流程编码
	 * @param wfCode
	 */
	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}
	public String getWfCode() {
		return wfCode;
	}
	/**
	 * 流程名称
	 * @param wfName
	 */
	public void setWfName(String wfName) {
		this.wfName = wfName;
	}
	public String getWfName() {
		return wfName;
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
		return "监控工作流配置： {\"wfCode\": \""+wfCode+"\", \"wfName\": \""+wfName+"\", \"createDate\": \""+createDate+"\", \"createBy\": \""+createBy+"\"}";
	}
}