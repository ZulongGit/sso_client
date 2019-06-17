package com.krm.web.sys.model;

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
 * 系统升级日志javaBean
 * 2017-12-27
 */
@Table(name="SYS_UPDATE_LOG")
public class SysUpdateLog extends BaseEntity<SysUpdateLog>{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
	private	Long	year;			//年份
	private	String	day;			//更新日
	private	String	content;			//更新内容
	private	String	moreInfo;			//更多信息
	private	String	createBy;			//创建人
	private	Date	createDate;			//创建时间
	private	String	updateBy;			//最近修改人
	private	Date	updateDate;			//最近修改时间
	private	String	delFlag;			//逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public SysUpdateLog() {
    	super.setModuleName("系统升级日志功能");
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
	 * 年份
	 * @param year
	 */
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getYear() {
		return year;
	}

	/**
	 * 更新日
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}
	public String getDay() {
		return day;
	}

	/**
	 * 更新内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}

	/**
	 * 更多信息
	 * @param moreInfo
	 */
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	public String getMoreInfo() {
		return moreInfo;
	}

	/**
	 * 创建人
	 * @param updateLog
	 */
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	

	/**
	 * 创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
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
		return "系统升级日志： {\"id\": \""+id+"\", \"year\": \""+year+"\", \"day\": \""+day+"\", \"content\": \""+content+"\", \"moreInfo\": \""+moreInfo+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}