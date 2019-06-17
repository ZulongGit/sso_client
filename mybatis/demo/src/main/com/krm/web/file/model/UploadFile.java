package com.krm.web.file.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.krm.common.base.BaseEntity;

/**
 * 
 * @author Parker
 * 附件表javaBean
 * 2017-12-11
 */
@Table(name="UPLOAD_FILE")
public class UploadFile extends BaseEntity<UploadFile>{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
	private	String	docTitle;			//文档名
	private	String	fileName;			//文件名
	private	String	extName;			//文件后缀名
	private	String	fileSize;			//文件大小
	private	Integer	disporder;			//排序
	private	String	relateId;			//关联表主键
	private	String	tableName;			//关联表
	private	String	description;		//文件描述
	private	String	createBy;			//创建者
	private	Date	createDate;			//创建日期
	private	String	updateBy;			//修改者
	private	Date	updateDate;			//修改日期
	private	String	delFlag;			//删除标记
	
	public UploadFile() {
    	super.setModuleName("附件");
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
	 * 文档名
	 * @param docTitle
	 */
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getDocTitle() {
		return docTitle;
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
	 * 文件后缀名
	 * @param extName
	 */
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public String getExtName() {
		return extName;
	}

	/**
	 * 文件大小
	 * @param fileSize
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * 排序
	 * @param disporder
	 */
	public void setDisporder(Integer disporder) {
		this.disporder = disporder;
	}
	public Integer getDisporder() {
		return disporder;
	}

	/**
	 * 关联表主键
	 * @param relateId
	 */
	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}
	public String getRelateId() {
		return relateId;
	}

	/**
	 * 关联表
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return tableName;
	}

	/**
	 * 文件描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

	/**
	 * 创建者
	 * @param createBy
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * 创建日期
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 修改者
	 * @param updateBy
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * 修改日期
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 删除标记
	 * @param delFlag
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getDelFlag() {
		return delFlag;
	}

	@Override
	public String toString() {
		return "附件表： {\"id\": \""+id+"\", \"docTitle\": \""+docTitle+"\", \"fileName\": \""+fileName+"\", \"extName\": \""+extName+"\", \"fileSize\": \""+fileSize+"\", \"disporder\": \""+disporder+"\", \"relateId\": \""+relateId+"\", \"tableName\": \""+tableName+"\", \"description\": \""+description+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}