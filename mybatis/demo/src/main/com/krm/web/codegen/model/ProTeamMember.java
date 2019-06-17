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
 * 项目小组成员javaBean
 * 2018-04-21
 */
@Table(name="gen_pro_team_member")
public class ProTeamMember extends BaseEntity<ProTeamMember>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
    private String  proId;      //所属项目
    private String  memberId;      //成员名称
    private String  post;      //小组角色
    private String  division;      //分工
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public ProTeamMember() {
    	super.setModuleName("项目小组成员");
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
	 * 成员名称
	 * @param memberId
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberId() {
		return memberId;
	}
	/**
	 * 小组角色
	 * @param post
	 */
	public void setPost(String post) {
		this.post = post;
	}
	public String getPost() {
		return post;
	}
	/**
	 * 分工
	 * @param division
	 */
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDivision() {
		return division;
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
		return "项目小组成员： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"memberId\": \""+memberId+"\", \"post\": \""+post+"\", \"division\": \""+division+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}