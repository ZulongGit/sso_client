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
 * 数据导入模板javaBean
 * 2018-08-22
 */
@Table(name="demo_data_imp_model")
public class DemoDataImpModel extends BaseEntity<DemoDataImpModel>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="模板编号", filedAlign=1, sorts=1)
	@Id
    private String  modelCode;      //模板编号
	@ExcelField(filedTitle="模板名称", filedAlign=1, sorts=2)
    private String  modelName;      //模板名称
	@ExcelField(filedTitle="数据范围", filedAlign=1, sorts=3)
    private String  dtRange;      //数据范围
	@ExcelField(filedTitle="描述", filedAlign=1, sorts=4)
    private String  description;      //描述
	@ExcelField(filedTitle="版本", filedAlign=1, sorts=5)
    private String  modelVersion;      //版本
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=6)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=7)
    private String  createBy;      //创建人
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DemoDataImpModel() {
    	super.setModuleName("数据导入模板");
	}
	/**
	 * 模板编号
	 * @param modelCode
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	/**
	 * 模板名称
	 * @param modelName
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelName() {
		return modelName;
	}
	/**
	 * 数据范围
	 * @param dtRange
	 */
	public void setDtRange(String dtRange) {
		this.dtRange = dtRange;
	}
	public String getDtRange() {
		return dtRange;
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
	 * 版本
	 * @param modelVersion
	 */
	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}
	public String getModelVersion() {
		return modelVersion;
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
		return "数据导入模板： {\"modelCode\": \""+modelCode+"\", \"modelName\": \""+modelName+"\", \"dtRange\": \""+dtRange+"\", \"description\": \""+description+"\", \"modelVersion\": \""+modelVersion+"\", \"createDate\": \""+createDate+"\", \"createBy\": \""+createBy+"\"}";
	}
}