package com.krm.web.demofx.model;

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
 * 风险数据表javaBean
 * 2018-08-23
 */
@Table(name="demofxdata")
public class Demofxdata extends BaseEntity<Demofxdata>{
	
	private static final long serialVersionUID = 1L;
	
    private String  id;      //编号
	@ExcelField(filedTitle="数据名称", filedAlign=1, sorts=2)
    private String  sname;      //数据名称
	@ExcelField(filedTitle="类型", filedAlign=1, sorts=3)
    private String  stype;      //类型
	@ExcelField(filedTitle="风险模型", filedAlign=1, sorts=4)
    private String  smodel;      //风险模型
	@ExcelField(filedTitle="数据日期", filedAlign=1, sorts=5)
    private String  sdate;      //数据日期
	@ExcelField(filedTitle="数据描述", filedAlign=1, sorts=6)
    private String  sdes;      //数据描述
	@ExcelField(filedTitle="数据来源", filedAlign=1, sorts=7)
    private String  scome;      //数据来源
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public Demofxdata() {
    	super.setModuleName("风险数据表");
	}
	/**
	 * 编号
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	/**
	 * 数据名称
	 * @param sname
	 */
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSname() {
		return sname;
	}
	/**
	 * 类型
	 * @param stype
	 */
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getStype() {
		return stype;
	}
	/**
	 * 风险模型
	 * @param smodel
	 */
	public void setSmodel(String smodel) {
		this.smodel = smodel;
	}
	public String getSmodel() {
		return smodel;
	}
	/**
	 * 数据日期
	 * @param sdate
	 */
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getSdate() {
		return sdate;
	}
	/**
	 * 数据描述
	 * @param sdes
	 */
	public void setSdes(String sdes) {
		this.sdes = sdes;
	}
	public String getSdes() {
		return sdes;
	}
	/**
	 * 数据来源
	 * @param scome
	 */
	public void setScome(String scome) {
		this.scome = scome;
	}
	public String getScome() {
		return scome;
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
		return "风险数据表： {\"id\": \""+id+"\", \"sname\": \""+sname+"\", \"stype\": \""+stype+"\", \"smodel\": \""+smodel+"\", \"sdate\": \""+sdate+"\", \"sdes\": \""+sdes+"\", \"scome\": \""+scome+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}