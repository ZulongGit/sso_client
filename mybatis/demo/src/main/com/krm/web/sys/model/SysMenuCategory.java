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
 * 菜单分类javaBean
 * 2018-08-02
 */
@Table(name="sys_menu_category")
public class SysMenuCategory extends BaseEntity<SysMenuCategory>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
	@Transient
	private String  parentId;      //
    private String  name;      //名称
    private String  code;      //编号
    private String  url;      //链接
    private String  icon;      //图标
    private String  description;      //描述
    private Long  sort;      //排序
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public SysMenuCategory() {
    	super.setModuleName("菜单分类");
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
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	/**
	 * 编号
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	/**
	 * 链接
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
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
	 * 排序
	 * @param sort
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public Long getSort() {
		return sort;
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
		return "菜单分类： {\"id\": \""+id+"\", \"name\": \""+name+"\", \"code\": \""+code+"\", \"url\": \""+url+"\", \"icon\": \""+icon+"\", \"description\": \""+description+"\", \"sort\": \""+sort+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}