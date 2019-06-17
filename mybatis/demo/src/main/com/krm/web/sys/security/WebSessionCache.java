package com.krm.web.sys.security;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 该类是缓存工具类，提供session的存储和获取等方法
 * 
 */
public class WebSessionCache implements Cache<Serializable, Session> {
	// 模拟缓存存储session对象
	private static final Map<Serializable, Session> map = new HashMap<Serializable, Session>();

	public Session get(Serializable key) throws CacheException {
		// 根据key获取缓存中的session
		return map.get(key);
	}

	public Session put(Serializable key, Session value) throws CacheException {
		// 将session对象存入缓存
		map.put(key, value);
		return value;
	}

	public Session remove(Serializable key) throws CacheException {
		// 移除session中为key的对象
		Session session = get(key);
		if (session != null) {
			session.setAttribute(key, null);
			return session;
		}
		return null;
	}

	public void clear() throws CacheException {
		// 清除所有的session
		map.clear();
	}

	public int size() {
		// 返回session的数量
		return map.size();
	}

	public Set<Serializable> keys() {
		// 返回所有session的key
		return map.keySet();
	}

	public Collection<Session> values() {
		// 返回所有session
		return map.values();
	}
}
