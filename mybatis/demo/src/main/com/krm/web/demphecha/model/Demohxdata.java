package com.krm.web.demphecha.model;

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
 * 数据核查表javaBean
 * 2018-08-23
 */
@Table(name="demohxdata")
public class Demohxdata extends BaseEntity<Demohxdata>{
	
	private static final long serialVersionUID = 1L;
	
    private String  id;      //编号
	@ExcelField(filedTitle="核查数据", filedAlign=1, sorts=2)
    private String  sname;      //核查数据
	@ExcelField(filedTitle="核查日期", filedAlign=1, sorts=3)
    private String  sdate;      //核查日期
	@ExcelField(filedTitle="核查意见", filedAlign=1, sorts=4)
    private String  sdsc;      //核查意见
	@ExcelField(filedTitle="核查人", filedAlign=1, sorts=5)
    private String  suser;      //核查人
	@ExcelField(filedTitle="结果", filedAlign=1, sorts=6)
    private String  snext;      //结果
	@ExcelField(filedTitle="结果描述", filedAlign=1, sorts=7)
    private String  sdscc;      //结果描述
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public Demohxdata() {
    	super.setModuleName("数据核查表");
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
	 * 核查数据
	 * @param sname
	 */
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSname() {
		return sname;
	}
	/**
	 * 核查日期
	 * @param sdate
	 */
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getSdate() {
		return sdate;
	}
	/**
	 * 核查意见
	 * @param sdsc
	 */
	public void setSdsc(String sdsc) {
		this.sdsc = sdsc;
	}
	public String getSdsc() {
		return sdsc;
	}
	/**
	 * 核查人
	 * @param suser
	 */
	public void setSuser(String suser) {
		this.suser = suser;
	}
	public String getSuser() {
		return suser;
	}
	/**
	 * 结果
	 * @param snext
	 */
	public void setSnext(String snext) {
		this.snext = snext;
	}
	public String getSnext() {
		return snext;
	}
	/**
	 * 结果描述
	 * @param sdscc
	 */
	public void setSdscc(String sdscc) {
		this.sdscc = sdscc;
	}
	public String getSdscc() {
		return sdscc;
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
		return "数据核查表： {\"id\": \""+id+"\", \"sname\": \""+sname+"\", \"sdate\": \""+sdate+"\", \"sdsc\": \""+sdsc+"\", \"suser\": \""+suser+"\", \"snext\": \""+snext+"\", \"sdscc\": \""+sdscc+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}