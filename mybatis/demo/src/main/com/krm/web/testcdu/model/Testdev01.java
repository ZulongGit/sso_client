package com.krm.web.testcdu.model;

import java.util.*;

import com.krm.common.base.BaseEntity;
import com.krm.common.base.BaseFile;

import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Table;
import com.krm.common.utils.excel.annotation.ExcelField;

/**
 * 
 * @author zhaozulong
 * 报告javaBean
 * 2018-12-06
 */
@Table(name="testdev_01")
public class Testdev01 extends BaseEntity<Testdev01>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="报告编号", filedAlign=1, sorts=1)
    private String  id;      //报告编号
	@ExcelField(filedTitle="报告名称", filedAlign=1, sorts=2)
    private String  name;      //报告名称
	@ExcelField(filedTitle="报告日期", filedAlign=1, sorts=3)
    private String  bgdate;      //报告日期
	@ExcelField(filedTitle="分类", filedAlign=1, sorts=4, dictionType="CUSTOMERPPTYPE")
    private String  subclass;      //分类
	@ExcelField(filedTitle="报告类型", filedAlign=1, sorts=5)
    private String  bgtype;      //报告类型
	@ExcelField(filedTitle="模板", filedAlign=1, sorts=6)
    private String  bttemp;      //模板
	@ExcelField(filedTitle="报告描述", filedAlign=1, sorts=7)
    private String  btdes;      //报告描述
	@ExcelField(filedTitle="报告人", filedAlign=1, sorts=8)
    private String  btcerter;      //报告人
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=9)
    private String  createBy;      //创建人
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=10)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="最近修改人", filedAlign=1, sorts=11)
    private String  updateBy;      //最近修改人
	@ExcelField(filedTitle="最近修改时间", filedAlign=1, sorts=12)
    private Date  updateDate;      //最近修改时间
	@ExcelField(filedTitle="null", filedAlign=1, sorts=13)
    private String  delFlag;      //null
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public Testdev01() {
    	super.setModuleName("报告");
	}
	/**
	 * 报告编号
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	/**
	 * 报告名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	/**
	 * 报告日期
	 * @param bgdate
	 */
	public void setBgdate(String bgdate) {
		this.bgdate = bgdate;
	}
	public String getBgdate() {
		return bgdate;
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
	 * 报告类型
	 * @param bgtype
	 */
	public void setBgtype(String bgtype) {
		this.bgtype = bgtype;
	}
	public String getBgtype() {
		return bgtype;
	}
	/**
	 * 模板
	 * @param bttemp
	 */
	public void setBttemp(String bttemp) {
		this.bttemp = bttemp;
	}
	public String getBttemp() {
		return bttemp;
	}
	/**
	 * 报告描述
	 * @param btdes
	 */
	public void setBtdes(String btdes) {
		this.btdes = btdes;
	}
	public String getBtdes() {
		return btdes;
	}
	/**
	 * 报告人
	 * @param btcerter
	 */
	public void setBtcerter(String btcerter) {
		this.btcerter = btcerter;
	}
	public String getBtcerter() {
		return btcerter;
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
	 * null
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
		return "报告： {\"id\": \""+id+"\", \"name\": \""+name+"\", \"bgdate\": \""+bgdate+"\", \"subclass\": \""+subclass+"\", \"bgtype\": \""+bgtype+"\", \"bttemp\": \""+bttemp+"\", \"btdes\": \""+btdes+"\", \"btcerter\": \""+btcerter+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}