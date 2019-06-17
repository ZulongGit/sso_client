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
 * 数据库管理javaBean
 * 2018-06-26
 */
@Table(name="gen_db_manag")
public class DbManag extends BaseEntity<DbManag>{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String  id;      //主键
	@ExcelField(filedTitle="所属项目", filedAlign=1, sorts=2)
    private String  proId;      //所属项目
	@ExcelField(filedTitle="别名", filedAlign=1, sorts=3)
    private String  alias;      //别名
	@ExcelField(filedTitle="数据库类型", filedAlign=1, sorts=4, dictionType="db_type")
    private String  dbType;      //数据库类型
	@ExcelField(filedTitle="数据库驱动", filedAlign=1, sorts=5)
    private String  driver;      //数据库驱动
	@ExcelField(filedTitle="数据库IP地址", filedAlign=1, sorts=6)
    private String  dbAddress;      //数据库IP地址
	@ExcelField(filedTitle="数据库端口", filedAlign=1, sorts=7)
    private String  dbPort;      //数据库端口
	@ExcelField(filedTitle="实例/数据库", filedAlign=1, sorts=8)
    private String  tbSchema;      //实例/数据库
	@ExcelField(filedTitle="登录用户名", filedAlign=1, sorts=9)
    private String  userName;      //登录用户名
	@ExcelField(filedTitle="登录密码", filedAlign=1, sorts=10)
    private String  password;      //登录密码
    private String  createBy;      //创建人
    private Date  createDate;      //创建时间
    private String  updateBy;      //最近修改人
    private Date  updateDate;      //最近修改时间
    private String  delFlag;      //逻辑删除标记(0.正常，1.删除)
	@Transient
	private List<BaseFile>	upfileList;			//批量上传文件list
	
	public DbManag() {
    	super.setModuleName("数据库管理");
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
	 * 别名
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAlias() {
		return alias;
	}
	/**
	 * 数据库类型
	 * @param dbType
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getDbType() {
		return dbType;
	}
	/**
	 * 数据库驱动
	 * @param driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDriver() {
		return driver;
	}
	/**
	 * 数据库IP地址
	 * @param dbAddress
	 */
	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}
	public String getDbAddress() {
		return dbAddress;
	}
	/**
	 * 数据库端口
	 * @param dbPort
	 */
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbPort() {
		return dbPort;
	}
	/**
	 * 实例/数据库
	 * @param tbSchema
	 */
	public void setTbSchema(String tbSchema) {
		this.tbSchema = tbSchema;
	}
	public String getTbSchema() {
		return tbSchema;
	}
	/**
	 * 登录用户名
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	/**
	 * 登录密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
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
		return "数据库管理： {\"id\": \""+id+"\", \"proId\": \""+proId+"\", \"alias\": \""+alias+"\", \"dbType\": \""+dbType+"\", \"driver\": \""+driver+"\", \"dbAddress\": \""+dbAddress+"\", \"dbPort\": \""+dbPort+"\", \"tbSchema\": \""+tbSchema+"\", \"userName\": \""+userName+"\", \"password\": \""+password+"\", \"createBy\": \""+createBy+"\", \"createDate\": \""+createDate+"\", \"updateBy\": \""+updateBy+"\", \"updateDate\": \""+updateDate+"\", \"delFlag\": \""+delFlag+"\"}";
	}
}