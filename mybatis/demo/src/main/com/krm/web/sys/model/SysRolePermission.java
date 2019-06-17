package com.krm.web.sys.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;

/**
 * 
 * @author Parker
 * 角色操作权限配置信息javaBean
 * 2017-11-19
 */
@Table(name="SYS_ROLE_PERMISSION")
public class SysRolePermission extends BaseEntity<SysRolePermission>{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
	private	String	roleId;			//角色ID
	private	String	menuId;			//菜单ID
	private	String	basePermission;			//基础权限名
	private	String	canAdd;			//增加
	private	String	canUpdate;			//修改
	private	String	canDelete;			//删除
	private	String	canQuery;			//查询
	private	String	canImport;			//导入
	private	String	canExport;			//导出
	@Transient
	private	String	name;			//菜单名，用来显示
	@Transient
	private String parentId;
	@Transient
	private	List<SysRolePermission> list;
	
	public SysRolePermission() {
    	super.setModuleName("角色操作权限配置信息");
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
	 * 角色ID
	 * @param roleId
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleId() {
		return roleId;
	}

	/**
	 * 菜单ID
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuId() {
		return menuId;
	}

	/**
	 * 基础权限名
	 * @param basePermission
	 */
	public void setBasePermission(String basePermission) {
		this.basePermission = basePermission;
	}
	public String getBasePermission() {
		return basePermission;
	}

	/**
	 * 增加
	 * @param canAdd
	 */
	public void setCanAdd(String canAdd) {
		this.canAdd = canAdd;
	}
	public String getCanAdd() {
		return canAdd;
	}

	/**
	 * 修改
	 * @param canUpdate
	 */
	public void setCanUpdate(String canUpdate) {
		this.canUpdate = canUpdate;
	}
	public String getCanUpdate() {
		return canUpdate;
	}

	/**
	 * 删除
	 * @param canDelete
	 */
	public void setCanDelete(String canDelete) {
		this.canDelete = canDelete;
	}
	public String getCanDelete() {
		return canDelete;
	}

	/**
	 * 查询
	 * @param canQuery
	 */
	public void setCanQuery(String canQuery) {
		this.canQuery = canQuery;
	}
	public String getCanQuery() {
		return canQuery;
	}

	/**
	 * 导入
	 * @param canImport
	 */
	public void setCanImport(String canImport) {
		this.canImport = canImport;
	}
	public String getCanImport() {
		return canImport;
	}

	/**
	 * 导出
	 * @param canExport
	 */
	public void setCanExport(String canExport) {
		this.canExport = canExport;
	}
	public String getCanExport() {
		return canExport;
	}

	/**
	 * 菜单名，用来显示
	 * @param name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<SysRolePermission> getList() {
		return list;
	}

	public void setList(List<SysRolePermission> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "角色操作权限配置表： {\"id\": \""+id+"\", \"roleId\": \""+roleId+"\", \"menuId\": \""+menuId+"\", \"basePermission\": \""+basePermission+"\", \"canAdd\": \""+canAdd+"\", \"canUpdate\": \""+canUpdate+"\", \"canDelete\": \""+canDelete+"\", \"canQuery\": \""+canQuery+"\", \"canImport\": \""+canImport+"\", \"canExport\": \""+canExport+"\"}";
	}
}