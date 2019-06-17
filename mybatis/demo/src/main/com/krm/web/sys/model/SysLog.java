package com.krm.web.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;

/**
 * 
 * @author Parker
 *
 */
@Table(name="sys_log")
public class SysLog extends BaseEntity<SysLog> {

	private static final long serialVersionUID = 1L;
	@Id
	private String id; 				//主键
    private String description; //description <>
	@Column(name="EXCEPTION_")
    private String exception; //exception <异常信息>
    private String method; //method <操作方式>
    private String params; //params <操作提交的数据>
    private String remoteAddr; //remote_addr <操作IP地址>
    private String requestUri; //request_uri <请求URI>
    private String type; //type <日志类型>
    private String userAgent; //user_agent <用户代理>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>

    @Transient
    public static final String TYPE_ACCESS = "1"; //操作日志
    @Transient
	public static final String TYPE_EXCEPTION = "2"; //异常日志
    
	public SysLog() {
		super();
		super.setModuleName("日志信息");
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

	public String getDescription() {
		return description;
	}
	public String getException() {
		return exception;
	}
	public String getMethod() {
		return method;
	}
	public String getParams() {
		return params;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public String getType() {
		return type;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public String getCreateBy() {
		return createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
