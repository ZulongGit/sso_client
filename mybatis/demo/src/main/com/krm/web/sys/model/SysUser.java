

package com.krm.web.sys.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;
import com.krm.common.constant.Constant;

/**
 * 
 * @author Parker
 *
 */
@Table(name="sys_user")
public class SysUser extends BaseEntity<SysUser>{

	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
    private String email; //email <邮箱>
    private Date loginDate; //login_date <最后登陆时间>
    private String loginIp; //login_ip <最后登陆IP>
    private String mobile; //mobile <手机>
    private String name; //name <姓名>
    private String no; //no <工号>
    private String organId; //organ_id <归属机构>
    private String deptId; //company_id <归属公司>
    private String password; //password <密码>
    private String phone; //phone <电话>
    private String remarks; //remarks <备注信息>
    private String username; //username <登录名>
    private String userType; //user_type <用户类型>
    private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>
	private String status; //status <状态>
    
    @Transient
    private String[] roleIds; //角色
    
    public SysUser() {
    	super.setModuleName("用户信息");
	}

	public SysUser(String deptId, String email, String mobile, String name,
			String no, String organId, String password, String phone,
			String remarks, String username, String userType, String[] roleIds) {
		super();
		this.deptId = deptId;
		this.email = email;
		this.mobile = mobile;
		this.name = name;
		this.no = no;
		this.organId = organId;
		this.password = password;
		this.phone = phone;
		this.remarks = remarks;
		this.username = username;
		this.userType = userType;
		this.roleIds = roleIds;
	}

    
    //是否是超级管理员
    public boolean isAdmin(){
    	return Constant.SUPER_ADMIN.equals(this.getUserType())?true:false;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public String getNo() {
		return no;
	}

	public String getOrganId() {
		return organId;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getUsername() {
		return username;
	}

	public String getUserType() {
		return userType;
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

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
    
}
