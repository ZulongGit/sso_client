

package com.krm.web.sys.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;


@Table(name="sys_organ")
public class SysOrgan extends BaseEntity<SysOrgan>{

	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
	private String code; 		//机构号
    private String address; 	//<地址>
    private String areaId; 		//<区域>
    private String email; 		//email <邮件>
    private String fax; 		//fax <传真>
    private String grade; 		//grade 
    private String master; 		//master 负责人
    private String name; 		//name <名称>
    private String parentId; 	//parent_id <上级机构号>
    private String parentIds; 	//parent_ids <所有上级机构号>
    private String phone; 		//phone <电话>
    private String remarks; 	//remarks <机构描述>
    private String type; 		//type <机构类别（机构/部门）>
    private String zipCode; 	//zip_code <邮政编码>
    private String icon; 		//icon <图标>
    private String updateBy; 	//update_by <最近修改人>
	private Date updateDate; 	//update_date <最近修改时间>
	private String createBy; 	//create_by <创建人>
	private Date createDate; 	//create_date <创建时间>
	private String delFlag; 	//del_flag <假删除标记(0.正常  1.删除)>
    
    @Transient
    private String oldParentIds; //
    
	public SysOrgan(){
		super.setModuleName("机构信息");
	}
	
	public SysOrgan(String id, String address, String areaId, String code,
			String email, String fax, String grade, String master, String name,
			String parentId, String parentIds, String phone, String remarks,
			String type, String zipCode, String icon, String updateBy,
			Date updateDate, String createBy, Date createDate, String delFlag,
			String oldParentIds)
	{
		super();
		this.id = id;
		this.address = address;
		this.areaId = areaId;
		this.code = code;
		this.email = email;
		this.fax = fax;
		this.grade = grade;
		this.master = master;
		this.name = name;
		this.parentId = parentId;
		this.parentIds = parentIds;
		this.phone = phone;
		this.remarks = remarks;
		this.type = type;
		this.zipCode = zipCode;
		this.icon = icon;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.createBy = createBy;
		this.createDate = createDate;
		this.delFlag = delFlag;
		this.oldParentIds = oldParentIds;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}


	public String getAreaId() {
		return areaId;
	}


	public String getCode() {
		return code;
	}


	public String getEmail() {
		return email;
	}


	public String getFax() {
		return fax;
	}


	public String getGrade() {
		return grade;
	}


	public String getMaster() {
		return master;
	}


	public String getName() {
		return name;
	}


	public String getParentId() {
		return parentId;
	}


	public String getParentIds() {
		return parentIds;
	}


	public String getPhone() {
		return phone;
	}


	public String getRemarks() {
		return remarks;
	}


	public String getType() {
		return type;
	}


	public String getZipCode() {
		return zipCode;
	}


	public String getIcon() {
		return icon;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public String getCreateBy() {
		return createBy;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public String getOldParentIds() {
		return oldParentIds;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public void setMaster(String master) {
		this.master = master;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public void setOldParentIds(String oldParentIds) {
		this.oldParentIds = oldParentIds;
	}

	@Override
	public String toString() {
		return "机构信息表： {\"id\": \""+id+"\", \"code\": \""+code+"\", \"name\": \""+name+"\", \"parentId\": \""+parentId+"\", \"areaId\": \""+areaId+"\", \"type\": \""+type+"\", \"grade\": \""+grade+"\", \"address\": \""+address+"\", \"zipCode\": \""+zipCode+"\", \"master\": \""+master+"\", \"phone\": \""+phone+"\", \"fax\": \""+fax+"\", \"email\": \""+email+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"remarks\": \""+remarks+"\", \"delFlag\": \""+delFlag+"\", \"icon\": \""+icon+"\", \"parentIds\": \""+parentIds+"\"}";
	}
}
