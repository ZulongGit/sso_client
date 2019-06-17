package com.krm.web.codegen.model;

import java.util.*;

import com.krm.common.base.BaseEntity;
import com.krm.common.base.BaseFile;

import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Table;
import com.krm.common.utils.excel.annotation.ExcelField;

/**
 * 
 * @author Parker
 * 枚举类数据字典javaBean
 * 2018-05-28
 */
@Table(name="gen_dict")
public class GenDict extends BaseEntity<GenDict>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
	@ExcelField(filedTitle="所属项目", filedAlign=1, sorts=2, sphereType=Projects.class)
    private String  proId;      //所属项目
	@ExcelField(filedTitle="标签", filedAlign=1, sorts=3)
    private String  label;      //标签
	@ExcelField(filedTitle="值", filedAlign=1, sorts=4)
    private String  value;      //值
	@ExcelField(filedTitle="类型", filedAlign=1, sorts=5)
    private String  type;      //类型
	@ExcelField(filedTitle="描述", filedAlign=1, sorts=6)
    private String  description;      //描述
	@ExcelField(filedTitle="排序", filedAlign=1, sorts=7)
    private Integer  sort;      //排序
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  remarks;      //备注
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public GenDict() {
    	super.setModuleName("枚举类数据字典");
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
	 * 类型
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
		return "枚举类数据字典： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"label\": \""+label+"\", \"value\": \""+value+"\", \"type\": \""+type+"\", \"description\": \""+description+"\", \"sort\": \""+sort+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"remarks\": \""+remarks+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}