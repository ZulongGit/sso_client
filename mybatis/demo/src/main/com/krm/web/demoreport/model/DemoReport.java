package com.krm.web.demoreport.model;

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
 * 系统报表javaBean
 * 2018-08-21
 */
@Table(name="demo_report")
public class DemoReport extends BaseEntity<DemoReport>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //编号
	@ExcelField(filedTitle="名称", filedAlign=1, sorts=2)
    private String  name;      //名称
	@ExcelField(filedTitle="分类", filedAlign=1, sorts=3)
    private String  subclass;      //分类
	@ExcelField(filedTitle="描述", filedAlign=1, sorts=4)
    private String  des;      //描述
	@ExcelField(filedTitle="负责人", filedAlign=1, sorts=5)
    private String  createor;      //负责人
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=6)
    private String  repdate;      //创建时间
    private Long  status;      //状态
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DemoReport() {
    	super.setModuleName("系统报表");
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
	 * 名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	/**
	 * 分类
	 * @param subclass
	 */
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	public String getSubclass() {
		return subclass;
	}
	/**
	 * 描述
	 * @param des
	 */
	public void setDes(String des) {
		this.des = des;
	}
	public String getDes() {
		return des;
	}
	/**
	 * 负责人
	 * @param createor
	 */
	public void setCreateor(String createor) {
		this.createor = createor;
	}
	public String getCreateor() {
		return createor;
	}
	/**
	 * 创建时间
	 * @param repdate
	 */
	public void setRepdate(String repdate) {
		this.repdate = repdate;
	}
	public String getRepdate() {
		return repdate;
	}
	/**
	 * 状态
	 * @param status
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getStatus() {
		return status;
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
		return "系统报表： {\"id\": \""+id+"\", \"name\": \""+name+"\", \"subclass\": \""+subclass+"\", \"des\": \""+des+"\", \"createor\": \""+createor+"\", \"repdate\": \""+repdate+"\", \"status\": \""+status+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}