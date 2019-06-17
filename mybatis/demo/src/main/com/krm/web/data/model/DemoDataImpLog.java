package com.krm.web.data.model;

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
 * 数据导入记录javaBean
 * 2018-08-22
 */
@Table(name="demo_data_imp_log")
public class DemoDataImpLog extends BaseEntity<DemoDataImpLog>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="ID", filedAlign=1, sorts=1)
    private String  pkid;      //ID
	@ExcelField(filedTitle="源数据", filedAlign=1, sorts=2)
    private String  dtSource;      //源数据
	@ExcelField(filedTitle="源数据集", filedAlign=1, sorts=3)
    private String  dtCollection;      //源数据集
	@ExcelField(filedTitle="模板ID", filedAlign=1, sorts=4)
    private String  dtModelCode;      //模板ID
	@ExcelField(filedTitle="模板名称", filedAlign=1, sorts=5)
    private String  dtModelName;      //模板名称
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=6)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=7)
    private String  createBy;      //创建人
	@ExcelField(filedTitle="数据条数", filedAlign=1, sorts=8)
    private Long  dtNum;      //数据条数
	@ExcelField(filedTitle="描述", filedAlign=1, sorts=9)
    private String  description;      //描述
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DemoDataImpLog() {
    	super.setModuleName("数据导入记录");
	}
	/**
	 * ID
	 * @param pkid
	 */
	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	public String getPkid() {
		return pkid;
	}
	/**
	 * 源数据
	 * @param dtSource
	 */
	public void setDtSource(String dtSource) {
		this.dtSource = dtSource;
	}
	public String getDtSource() {
		return dtSource;
	}
	/**
	 * 源数据集
	 * @param dtCollection
	 */
	public void setDtCollection(String dtCollection) {
		this.dtCollection = dtCollection;
	}
	public String getDtCollection() {
		return dtCollection;
	}
	/**
	 * 模板ID
	 * @param dtModelCode
	 */
	public void setDtModelCode(String dtModelCode) {
		this.dtModelCode = dtModelCode;
	}
	public String getDtModelCode() {
		return dtModelCode;
	}
	/**
	 * 模板名称
	 * @param dtModelName
	 */
	public void setDtModelName(String dtModelName) {
		this.dtModelName = dtModelName;
	}
	public String getDtModelName() {
		return dtModelName;
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
	 * 数据条数
	 * @param dtNum
	 */
	public void setDtNum(Long dtNum) {
		this.dtNum = dtNum;
	}
	public Long getDtNum() {
		return dtNum;
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
		return "数据导入记录： {\"pkid\": \""+pkid+"\", \"dtSource\": \""+dtSource+"\", \"dtCollection\": \""+dtCollection+"\", \"dtModelCode\": \""+dtModelCode+"\", \"dtModelName\": \""+dtModelName+"\", \"createDate\": \""+createDate+"\", \"createBy\": \""+createBy+"\", \"dtNum\": \""+dtNum+"\", \"description\": \""+description+"\"}";
	}
}