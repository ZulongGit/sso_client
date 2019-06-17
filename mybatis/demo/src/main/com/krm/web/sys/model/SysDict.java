

package com.krm.web.sys.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.krm.common.base.BaseEntity;


/**
 * 
 * @author Parker
 *
 */
@Table(name="sys_dict")
public class SysDict extends BaseEntity<SysDict> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2769085433136972388L;
	@Id
	private String id; 				//主键
	private String description; //description <描述>
    private String label; //label <标签名>
    private String remarks; //remarks <备注信息>
    private Integer sort; //sort <排序（升序）>
    private String type; //type <类型>
    private String value; //value <数据值>
    private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>

	public SysDict() {
		super();
		super.setModuleName("系统字典");
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
	 * 标签
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}

	/**
	 * 值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

	/**
	 * 字典类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
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
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSort() {
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
	 * 备注
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
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


	@Override
	public String toString() {
		return "系统字典表： {\"id\": \""+id+"\", \"label\": \""+label+"\", \"value\": \""+value+"\", \"type\": \""+type+"\", \"description\": \""+description+"\", \"sort\": \""+sort+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"remarks\": \""+remarks+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}
