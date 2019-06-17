package com.krm.web.pageset.model;

import java.util.*;

import com.krm.common.base.BaseEntity;
import com.krm.common.base.BaseFile;

import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Table;
import com.krm.common.utils.excel.annotation.ExcelField;

/**
 * 
 * @author fanxiaofeng
 * 分析页面表javaBean
 * 2018-10-10
 */
@Table(name="kbis_page")
public class KbisPage extends BaseEntity<KbisPage>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //编号
	@ExcelField(filedTitle="页面名称", filedAlign=1, sorts=2)
    private String  pageName;      //页面名称
	@ExcelField(filedTitle="页面描述", filedAlign=1, sorts=3)
    private String  pageDesc;      //页面描述
	@ExcelField(filedTitle="页面内容", filedAlign=1, sorts=4)
    private String  pageContent;      //页面内容
	@ExcelField(filedTitle="数据绑定", filedAlign=1, sorts=5)
    private String  pageData;      //数据绑定
	@ExcelField(filedTitle="页面地址", filedAlign=1, sorts=6)
    private String  pageUrl;      //页面地址
	@ExcelField(filedTitle="页面排序", filedAlign=1, sorts=7)
    private Integer  pageStor;      //页面排序
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
	@ExcelField(filedTitle="逻辑删除标记(0.正常，1.删除)", filedAlign=1, sorts=12)
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public KbisPage() {
    	super.setModuleName("分析页面表");
	}
	/**
	 * 编号
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	/**
	 * 页面名称
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageName() {
		return pageName;
	}
	/**
	 * 页面描述
	 * @param pageDesc
	 */
	public void setPageDesc(String pageDesc) {
		this.pageDesc = pageDesc;
	}
	public String getPageDesc() {
		return pageDesc;
	}
	/**
	 * 页面内容
	 * @param pageContent
	 */
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public String getPageContent() {
		return pageContent;
	}
	/**
	 * 数据绑定
	 * @param pageData
	 */
	public void setPageData(String pageData) {
		this.pageData = pageData;
	}
	public String getPageData() {
		return pageData;
	}
	/**
	 * 页面地址
	 * @param pageUrl
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * 页面排序
	 * @param pageStor
	 */
	public void setPageStor(Integer pageStor) {
		this.pageStor = pageStor;
	}
	public Integer getPageStor() {
		return pageStor;
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
		return "分析页面表： {\"id\": \""+id+"\", \"pageName\": \""+pageName+"\", \"pageDesc\": \""+pageDesc+"\", \"pageContent\": \""+pageContent+"\", \"pageData\": \""+pageData+"\", \"pageUrl\": \""+pageUrl+"\", \"pageStor\": \""+pageStor+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}