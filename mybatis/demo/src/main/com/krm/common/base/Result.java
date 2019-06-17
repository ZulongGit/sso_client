package com.krm.common.base;

import java.io.Serializable;

/**
 * 响应结果
 * 
 * @author Administrator
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7404778615369837901L;
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	/**
	 * 警告
	 */
	public static final int WARN = 2;
	/**
	 * 失败
	 */
	public static final int ERROR = 0;

	/**
	 * 成功消息
	 */
	public static final String SUCCESS_MSG = "操作成功！";
	/**
	 * 失败消息
	 */
	public static final String ERROR_MSG = "操作失败，请稍后重试！";

	/**
	 * 结果状态码(可自定义结果状态码) 1:操作成功 0:操作失败
	 */
	private int code;
	/**
	 * 响应结果描述
	 */
	private String msg;
	/**
	 * 其它数据信息（比如跳转地址）
	 */
	private Object data;

	public Result() {
		super();
	}

	/**
	 * 
	 * @param code 结果状态码(可自定义结果状态码或者使用内部静态变量 1:操作成功 0:操作失败 2:警告)
	 * @param msg 响应结果描述
	 * @param data 其它数据信息（比如跳转地址）
	 */
	public Result(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Result(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}


	/**
	 * 默认操作成功结果.
	 */
	public static Result successResult() {
		return new Result(SUCCESS, SUCCESS_MSG, null);
	}
	
	/**
	 * 默认操作失败结果.
	 */
	public static Result errorResult() {
		return new Result(ERROR, ERROR_MSG, null);
	}
	/**
	 * 默认操作成功结果.
	 */
	public static Result successResult(Object data) {
		return new Result(SUCCESS, SUCCESS_MSG, data);
	}

	/**
	 * 默认操作失败结果.
	 */
	public static Result errorResult(Object data) {
		return new Result(ERROR, ERROR_MSG, data);
	}

	/**
	 * 结果状态码(可自定义结果状态码) 1:操作成功 0:操作失败
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置结果状态码(约定 1:操作成功 0:操作失败 2:警告)
	 * 
	 * @param code 结果状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 响应结果描述
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置响应结果描述
	 * 
	 * @param msg 响应结果描述
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 其它数据信息（比如跳转地址）
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置其它数据信息（比如跳转地址）
	 * 
	 * @param data 其它数据信息（比如跳转地址）
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
