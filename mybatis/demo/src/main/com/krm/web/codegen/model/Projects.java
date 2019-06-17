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
 * 项目javaBean
 * 2018-04-23
 */
@Table(name="gen_projects")
public class Projects extends BaseEntity<Projects>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
    private String  proName;      //项目名称
    private String  description;      //项目描述
    private Integer  sorts;      //排序
    private String  proType;      //项目类型
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public Projects() {
    	super.setModuleName("项目");
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
	 * 项目名称
	 * @param proName
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProName() {
		return proName;
	}
	/**
	 * 项目描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	/**
	 * 排序
	 * @param sorts
	 */
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}
	public Integer getSorts() {
		return sorts;
	}
	/**
	 * 项目类型
	 * @param proType
	 */
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getProType() {
		return proType;
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
		return "项目： {\"id\": \""+id+"\", \"proName\": \""+proName+"\", \"description\": \""+description+"\", \"sorts\": \""+sorts+"\", \"proType\": \""+proType+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}