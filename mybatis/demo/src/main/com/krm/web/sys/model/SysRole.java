

package com.krm.web.sys.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;

/**
 * 
 * @author Parker
 *
 */
@Table(name="sys_role")
public class SysRole extends BaseEntity<SysRole> {

	private static final long serialVersionUID = 1L;

	@Id
	private String id; 				//主键
    private String dataScope; //data_scope <数据范围>
    private String name; //name <角色名称>
    private String organId; //organ_id <归属机构>
    private String remarks; //remarks <备注信息>
    private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>

    @Transient
    private String[] menuIds; //持有的菜单
    @Transient 
    private String[] organIds; //机构
    @Transient
    private String[] userIds; //绑定的用户
    
	public SysRole() {
		super();
		super.setModuleName("角色信息");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataScope() {
		return dataScope;
	}
	public String getName() {
		return name;
	}
	public String getOrganId() {
		return organId;
	}
	public String getRemarks() {
		return remarks;
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
	public String[] getMenuIds() {
		return menuIds;
	}
	public String[] getOrganIds() {
		return organIds;
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public void setMenuIds(String[] menuIds) {
		this.menuIds = menuIds;
	}
	public void setOrganIds(String[] organIds) {
		this.organIds = organIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	
	@Override
	public String toString() {
		return "角色表： {\"id\": \""+id+"\", \"organId\": \""+organId+"\", \"name\": \""+name+"\", \"dataScope\": \""+dataScope+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"remarks\": \""+remarks+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}
