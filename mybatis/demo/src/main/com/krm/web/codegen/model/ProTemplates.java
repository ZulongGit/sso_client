package com.krm.web.codegen.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;
import com.krm.common.base.BaseFile;

/**
 * 
 * @author Parker
 * 模板配置javaBean
 * 2018-04-11
 */
@Table(name="gen_pro_templates")
public class ProTemplates extends BaseEntity<ProTemplates>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
    private String  proId;      //所属项目
    private String  templateName;      //模板名称
    private String  remarks;      //描述
    private String  fileName;      //文件名
    private String  path;      //路径
    private String  genType;      //生成类型
    private String  tempLanguage;      //语言
    private Long  sorts;      //排序
    private Long  canDel;      //能否删除
    private String  contents;      //模板内容
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public ProTemplates() {
    	super.setModuleName("模板配置");
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
	 * 模板名称
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * 描述
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	/**
	 * 路径
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	/**
	 * 生成类型
	 * @param genType
	 */
	public void setGenType(String genType) {
		this.genType = genType;
	}
	public String getGenType() {
		return genType;
	}
	/**
	 * 语言
	 * @param tempLanguage
	 */
	public void setTempLanguage(String tempLanguage) {
		this.tempLanguage = tempLanguage;
	}
	public String getTempLanguage() {
		return tempLanguage;
	}
	/**
	 * 排序
	 * @param sorts
	 */
	public void setSorts(Long sorts) {
		this.sorts = sorts;
	}
	public Long getSorts() {
		return sorts;
	}
	/**
	 * 能否删除
	 * @param canDel
	 */
	public void setCanDel(Long canDel) {
		this.canDel = canDel;
	}
	public Long getCanDel() {
		return canDel;
	}
	/**
	 * 模板内容
	 * @param contents
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getContents() {
		return contents;
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
		return "模板配置： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"templateName\": \""+templateName+"\", \"remarks\": \""+remarks+"\", \"fileName\": \""+fileName+"\", \"path\": \""+path+"\", \"genType\": \""+genType+"\", \"tempLanguage\": \""+tempLanguage+"\", \"sorts\": \""+sorts+"\", \"canDel\": \""+canDel+"\", \"contents\": \""+contents+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}