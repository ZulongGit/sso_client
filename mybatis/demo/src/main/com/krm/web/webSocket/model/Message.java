package com.krm.web.webSocket.model;

import java.io.Serializable;

import com.krm.common.utils.DateUtils;

/**
 * 说明：消息实体类
 * @author Parker
 *
 */
public class Message implements Serializable {
	private static final long serialVersionUID = -1792012918715300761L;
	private String userName;	//用户，即发送者
    private String content;		//消息内容
    private String sendTime = DateUtils.getDateTime();	//发送时间
    private String type;		//消息类型：1:上线/2:下线/3:发送消息/4:踢出/5:用户列表
    private String isAdmin = "0";	//是否管理员
    private String scope;		//发送消息范围：all:所有对象/one:个人
    private String sendTo;		//接收者

    public Message() {
    }

    
	/**
	 * @param userName
	 * @param content
	 * @param msgType
	 */
	public Message(String userName, String content, String type) {
		super();
		this.userName = userName;
		this.content = content;
		this.type = type;
	}


	public String getUserName() {
		return userName;
	}

	public String getContent() {
		return content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public String getType() {
		return type;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public String getScope() {
		return scope;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

    

}
