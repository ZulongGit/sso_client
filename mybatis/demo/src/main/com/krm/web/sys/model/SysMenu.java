

package com.krm.web.sys.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;


/**
 * 
 * @author 
 */

@Table(name="sys_menu")
public class SysMenu extends BaseEntity<SysMenu> {

	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
    private String common; //common <是否是公共资源(0.权限资源 1.公共资源)>
    private String description; //description <描述>
    private String icon; //icon <图标>
    private String name; //name <资源名称>
    private String parentId; //parent_id <父级id>
    private Integer sort; //sort <排序号>
    private String status; //status <状态(0.正常 1.禁用)>
    private String type; //type <类型(0.菜单 1.按钮)>
    private String url; //url <链接>
    private String parentIds;
    private	String	permissionStr;	//权限标识
    private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>
    
    @Transient
    private String oldParentIds; //旧的pids,非表中字段，用作更新用

	public SysMenu() {
		super();
		super.setModuleName("菜单信息");
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
	 * 菜单名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	/**
	 * 权限类型
	 * @param common
	 */
	public void setCommon(String common) {
		this.common = common;
	}
	public String getCommon() {
		return common;
	}

	/**
	 * 图标
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIcon() {
		return icon;
	}

	/**
	 * 排序
	 * @param sort
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSort() {
		return sort;
	}

	/**
	 * 上级菜单ID
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentId() {
		return parentId;
	}

	/**
	 * 菜单类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}

	/**
	 * URL
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}

	/**
	 * 描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

	/**
	 * 状态
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
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
	 * 逻辑删除标记(0.正常  1.删除)
	 * @param delFlag
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * 权限标识
	 * @param permissionStr
	 */
	public void setPermissionStr(String permissionStr) {
		this.permissionStr = permissionStr;
	}
	public String getPermissionStr() {
		return permissionStr;
	}

	/**
	 * 所有上级菜单ID
	 * @param parentIds
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getParentIds() {
		return parentIds;
	}
	
	
	/**
	 * 所有旧上级菜单ID
	 * @param oldParentIds
	 */
	public String getOldParentIds() {
		return oldParentIds;
	}
	public void setOldParentIds(String oldParentIds) {
		this.oldParentIds = oldParentIds;
	}

	@Override
	public String toString() {
		return "菜单表： {\"id\": \""+id+"\", \"name\": \""+name+"\", \"common\": \""+common+"\", \"icon\": \""+icon+"\", \"sort\": \""+sort+"\", \"parentId\": \""+parentId+"\", \"type\": \""+type+"\", \"url\": \""+url+"\", \"description\": \""+description+"\", \"status\": \""+status+"\", \"createDate\": \""+createDate+"\", \"updateDate\": \""+updateDate+"\", \"createBy\": \""+createBy+"\", \"updateBy\": \""+updateBy+"\", \"delFlag\": \""+delFlag+"\", \"permissionStr\": \""+permissionStr+"\", \"parentIds\": \""+parentIds+"\"}";
	}
}
