package com.krm.web.codegen.model;

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
 * 项目参数javaBean
 * 2018-04-11
 */
@Table(name="gen_pro_params")
public class ProParams extends BaseEntity<ProParams>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="主键", filedAlign=1, sorts=1)
	@Id
    private String  id;      //主键
	@ExcelField(filedTitle="项目根路径", filedAlign=1, sorts=2)
    private String  proPath;      //项目根路径
	@ExcelField(filedTitle="源码路径", filedAlign=1, sorts=3)
    private String  sourceFolder;      //源码路径
	@ExcelField(filedTitle="HTML路径", filedAlign=1, sorts=4)
    private String  htmlPath;      //HTML路径
	@ExcelField(filedTitle="XML路径", filedAlign=1, sorts=5)
    private String  xmlPath;      //XML路径
	@ExcelField(filedTitle="CODE父路径", filedAlign=1, sorts=6)
    private String  codePath;      //CODE父路径
	@ExcelField(filedTitle="JS路径", filedAlign=1, sorts=7)
    private String  jsPath;      //JS路径
	@ExcelField(filedTitle="所属项目", filedAlign=1, sorts=8)
    private String  proId;      //所属项目
	@ExcelField(filedTitle="别名", filedAlign=1, sorts=9)
    private String  alias;      //别名
	@ExcelField(filedTitle="作者", filedAlign=1, sorts=10)
    private String  author;      //作者
	@ExcelField(filedTitle="编码", filedAlign=1, sorts=11)
    private String  coding;      //编码
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=12)
    private String  createBy;      //创建人
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=13)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="最近修改人", filedAlign=1, sorts=14)
    private String  updateBy;      //最近修改人
	@ExcelField(filedTitle="最近修改时间", filedAlign=1, sorts=15)
    private Date  updateDate;      //最近修改时间
	@ExcelField(filedTitle="逻辑删除标记(0.正常，1.删除)", filedAlign=1, sorts=16)
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public ProParams() {
    	super.setModuleName("项目参数");
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
	 * 项目根路径
	 * @param proPath
	 */
	public void setProPath(String proPath) {
		this.proPath = proPath;
	}
	public String getProPath() {
		return proPath;
	}
	/**
	 * 源码路径
	 * @param sourceFolder
	 */
	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	public String getSourceFolder() {
		return sourceFolder;
	}
	/**
	 * HTML路径
	 * @param htmlPath
	 */
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	public String getHtmlPath() {
		return htmlPath;
	}
	/**
	 * XML路径
	 * @param xmlPath
	 */
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public String getXmlPath() {
		return xmlPath;
	}
	/**
	 * CODE父路径
	 * @param codePath
	 */
	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
	public String getCodePath() {
		return codePath;
	}
	/**
	 * JS路径
	 * @param jsPath
	 */
	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}
	public String getJsPath() {
		return jsPath;
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
	 * 别名
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAlias() {
		return alias;
	}
	/**
	 * 作者
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	/**
	 * 编码
	 * @param coding
	 */
	public void setCoding(String coding) {
		this.coding = coding;
	}
	public String getCoding() {
		return coding;
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
		return "项目参数： {\"id\": \""+id+"\", \"proPath\": \""+proPath+"\", \"sourceFolder\": \""+sourceFolder+"\", \"htmlPath\": \""+htmlPath+"\", \"xmlPath\": \""+xmlPath+"\", \"codePath\": \""+codePath+"\", \"jsPath\": \""+jsPath+"\", \"proId\": \""+proId+"\", \"alias\": \""+alias+"\", \"author\": \""+author+"\", \"coding\": \""+coding+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}