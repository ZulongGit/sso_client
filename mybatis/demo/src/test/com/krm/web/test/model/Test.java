package com.krm.web.test.model;

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
 * 测试javaBean
 * 2018-06-21
 */
@Table(name="test")
public class Test extends BaseEntity<Test>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
	@ExcelField(filedTitle="用户", filedAlign=1, sorts=2, dictionType="sys_user_select")
    private String  userId;      //用户
	@ExcelField(filedTitle="机构", filedAlign=1, sorts=3, dictionType="organ_select")
    private String  organNo;      //机构
	@ExcelField(filedTitle="字段1", filedAlign=1, sorts=4)
    private Double  coloum1;      //字段1
	@ExcelField(filedTitle="字段2", filedAlign=1, sorts=5)
    private String  coloum2;      //字段2
	@ExcelField(filedTitle="字段3", filedAlign=1, sorts=6)
    private String  coloum3;      //字段3
	@ExcelField(filedTitle="字段4", filedAlign=1, sorts=7)
    private String  coloum4;      //字段4
	@ExcelField(filedTitle="字段5", filedAlign=1, sorts=8)
    private String  coloum5;      //字段5
	@ExcelField(filedTitle="字段6", filedAlign=1, sorts=9)
    private String  coloum6;      //字段6
	@ExcelField(filedTitle="字段7", filedAlign=1, sorts=10)
    private String  coloum7;      //字段7
	@ExcelField(filedTitle="字段8", filedAlign=1, sorts=11)
    private String  coloum8;      //字段8
	@ExcelField(filedTitle="字段9", filedAlign=1, sorts=12)
    private String  coloum9;      //字段9
	@ExcelField(filedTitle="字段10", filedAlign=1, sorts=13, dictionType="field_type")
    private String  coloum10;      //字段10
	@ExcelField(filedTitle="富文本", filedAlign=1, sorts=14)
    private String  richText;      //富文本
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public Test() {
    	super.setModuleName("测试");
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
	 * 用户
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	/**
	 * 机构
	 * @param organNo
	 */
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}
	public String getOrganNo() {
		return organNo;
	}
	/**
	 * 字段1
	 * @param coloum1
	 */
	public void setColoum1(Double coloum1) {
		this.coloum1 = coloum1;
	}
	public Double getColoum1() {
		return coloum1;
	}
	/**
	 * 字段2
	 * @param coloum2
	 */
	public void setColoum2(String coloum2) {
		this.coloum2 = coloum2;
	}
	public String getColoum2() {
		return coloum2;
	}
	/**
	 * 字段3
	 * @param coloum3
	 */
	public void setColoum3(String coloum3) {
		this.coloum3 = coloum3;
	}
	public String getColoum3() {
		return coloum3;
	}
	/**
	 * 字段4
	 * @param coloum4
	 */
	public void setColoum4(String coloum4) {
		this.coloum4 = coloum4;
	}
	public String getColoum4() {
		return coloum4;
	}
	/**
	 * 字段5
	 * @param coloum5
	 */
	public void setColoum5(String coloum5) {
		this.coloum5 = coloum5;
	}
	public String getColoum5() {
		return coloum5;
	}
	/**
	 * 字段6
	 * @param coloum6
	 */
	public void setColoum6(String coloum6) {
		this.coloum6 = coloum6;
	}
	public String getColoum6() {
		return coloum6;
	}
	/**
	 * 字段7
	 * @param coloum7
	 */
	public void setColoum7(String coloum7) {
		this.coloum7 = coloum7;
	}
	public String getColoum7() {
		return coloum7;
	}
	/**
	 * 字段8
	 * @param coloum8
	 */
	public void setColoum8(String coloum8) {
		this.coloum8 = coloum8;
	}
	public String getColoum8() {
		return coloum8;
	}
	/**
	 * 字段9
	 * @param coloum9
	 */
	public void setColoum9(String coloum9) {
		this.coloum9 = coloum9;
	}
	public String getColoum9() {
		return coloum9;
	}
	/**
	 * 字段10
	 * @param coloum10
	 */
	public void setColoum10(String coloum10) {
		this.coloum10 = coloum10;
	}
	public String getColoum10() {
		return coloum10;
	}
	/**
	 * 富文本
	 * @param richText
	 */
	public void setRichText(String richText) {
		this.richText = richText;
	}
	public String getRichText() {
		return richText;
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
		return "测试： {\"id\": \""+id+"\", \"userId\": \""+userId+"\", \"organNo\": \""+organNo+"\", \"coloum1\": \""+coloum1+"\", \"coloum2\": \""+coloum2+"\", \"coloum3\": \""+coloum3+"\", \"coloum4\": \""+coloum4+"\", \"coloum5\": \""+coloum5+"\", \"coloum6\": \""+coloum6+"\", \"coloum7\": \""+coloum7+"\", \"coloum8\": \""+coloum8+"\", \"coloum9\": \""+coloum9+"\", \"coloum10\": \""+coloum10+"\", \"richText\": \""+richText+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}