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
public class CommonEntity extends JSONObject {

	private static final long serialVersionUID = 1L;
	
	public CommonEntity() {
		super();
	}
	
	public CommonEntity(Map<String, Object> map) {
		super(map);
	}

	public CommonEntity getEntity(String key) {
		Object value = this.get(key);
		if (value instanceof CommonEntity) {
			return (CommonEntity) value;
		}
		
		JSONObject jobj = null;

		if (value instanceof JSONObject) {
			jobj = (JSONObject) value;
		} else {
			jobj = (JSONObject) toJSON(value);
		}

		return jobj == null ? null : new CommonEntity(jobj);
	}

	public CommonEntity set(String key, Object value, boolean force) {
		if (force || value != null) {
			super.put(key, value);
		}
		return this;
	}

	public CommonEntity set(String key, Object value) {
		return this.set(key, value, true);
	}

	public CommonEntity setAll(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
		return this;
	}
}
