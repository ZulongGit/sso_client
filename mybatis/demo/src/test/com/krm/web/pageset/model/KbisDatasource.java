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
 * 数据源javaBean
 * 2018-10-12
 */
@Table(name="kbis_datasource")
public class KbisDatasource extends BaseEntity<KbisDatasource>{
	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(filedTitle="编号", filedAlign=1, sorts=1)
	@Id
    private String  id;      //编号
	@ExcelField(filedTitle="数据源名称", filedAlign=1, sorts=2)
    private String  sName;      //数据源名称
	@ExcelField(filedTitle="数据源类型", filedAlign=1, sorts=3)
    private Integer  sType;      //数据源类型
	@ExcelField(filedTitle="源描述", filedAlign=1, sorts=4)
    private String  sDesc;      //源描述
	@ExcelField(filedTitle="地址", filedAlign=1, sorts=5)
    private String  sLocal;      //地址
	@ExcelField(filedTitle="连接", filedAlign=1, sorts=6)
    private String  sUrl;      //连接
	@ExcelField(filedTitle="连接用户", filedAlign=1, sorts=7)
    private String  sConn;      //连接用户
	@ExcelField(filedTitle="连接密码", filedAlign=1, sorts=8)
    private String  sPass;      //连接密码
	@ExcelField(filedTitle="协议", filedAlign=1, sorts=9)
    private String  sProtocol;      //协议
	@ExcelField(filedTitle="创建人", filedAlign=1, sorts=10)
    private String  createBy;      //创建人
	@ExcelField(filedTitle="创建时间", filedAlign=1, sorts=11)
    private Date  createDate;      //创建时间
	@ExcelField(filedTitle="最近修改人", filedAlign=1, sorts=12)
    private String  updateBy;      //最近修改人
	@ExcelField(filedTitle="最近修改时间", filedAlign=1, sorts=13)
    private Date  updateDate;      //最近修改时间
	@ExcelField(filedTitle="逻辑删除标记(0.正常，1.删除)", filedAlign=1, sorts=14)
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public KbisDatasource() {
    	super.setModuleName("数据源");
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
	 * 数据源名称
	 * @param sName
	 */
	public void setSName(String sName) {
		this.sName = sName;
	}
	public String getSName() {
		return sName;
	}
	/**
	 * 数据源类型
	 * @param sType
	 */
	public void setSType(Integer sType) {
		this.sType = sType;
	}
	public Integer getSType() {
		return sType;
	}
	/**
	 * 源描述
	 * @param sDesc
	 */
	public void setSDesc(String sDesc) {
		this.sDesc = sDesc;
	}
	public String getSDesc() {
		return sDesc;
	}
	/**
	 * 地址
	 * @param sLocal
	 */
	public void setSLocal(String sLocal) {
		this.sLocal = sLocal;
	}
	public String getSLocal() {
		return sLocal;
	}
	/**
	 * 连接
	 * @param sUrl
	 */
	public void setSUrl(String sUrl) {
		this.sUrl = sUrl;
	}
	public String getSUrl() {
		return sUrl;
	}
	/**
	 * 连接用户
	 * @param sConn
	 */
	public void setSConn(String sConn) {
		this.sConn = sConn;
	}
	public String getSConn() {
		return sConn;
	}
	/**
	 * 连接密码
	 * @param sPass
	 */
	public void setSPass(String sPass) {
		this.sPass = sPass;
	}
	public String getSPass() {
		return sPass;
	}
	/**
	 * 协议
	 * @param sProtocol
	 */
	public void setSProtocol(String sProtocol) {
		this.sProtocol = sProtocol;
	}
	public String getSProtocol() {
		return sProtocol;
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
		return "数据源： {\"id\": \""+id+"\", \"sName\": \""+sName+"\", \"sType\": \""+sType+"\", \"sDesc\": \""+sDesc+"\", \"sLocal\": \""+sLocal+"\", \"sUrl\": \""+sUrl+"\", \"sConn\": \""+sConn+"\", \"sPass\": \""+sPass+"\", \"sProtocol\": \""+sProtocol+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}