package com.krm.common.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "unused"})
@Entity
public class BaseEntitybak extends JSONObject {

	private static final long serialVersionUID = 1L;
	
	
	
	@Transient
	private String userDataScope; //用户的数据范围
	
	public BaseEntitybak() {
		super();
	}
	
	public void setId(Long id){
		this.set("id",id);
	}

	public Long getId(){
		return this.getLong("id");
	}
	
    public String getUserDataScope() {
		return this.getString("userDataScope");
    }
   
    public void setUserDataScope(String userDataScope) {
		this.set("userDataScope", userDataScope);
    }

	public BaseEntitybak(Map<String, Object> map) {
		super(map);
	}

	public BaseEntitybak getEntity(String key) {
		Object value = this.get(key);
		if (value instanceof BaseEntitybak) {
			return (BaseEntitybak) value;
		}
		
		JSONObject jobj = null;

		if (value instanceof JSONObject) {
			jobj = (JSONObject) value;
		} else {
			jobj = (JSONObject) toJSON(value);
		}

		return jobj == null ? null : new BaseEntitybak(jobj);
	}

	public BaseEntitybak set(String key, Object value, boolean force) {
		if (force || value != null) {
			super.put(key, value);
		}
		return this;
	}

	public BaseEntitybak set(String key, Object value) {
		return this.set(key, value, true);
	}

	public BaseEntitybak setAll(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
		return this;
	}

	public static BaseEntitybak err(int errCode) {
		return new BaseEntitybak().set("errCode", errCode);
	}

	public static BaseEntitybak err(int errCode, String errMsg) {
		return new BaseEntitybak().set("errCode", errCode).set("errMsg", errMsg);
	}
	
}
