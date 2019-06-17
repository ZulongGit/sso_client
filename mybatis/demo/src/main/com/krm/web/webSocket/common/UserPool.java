package com.krm.web.webSocket.common;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户池
 * @author Parker
 *
 */
public class UserPool {
    private static Map<String, JSONObject> USER_POOL = new HashMap<String, JSONObject>();

    public static void add(String userName, JSONObject nickName) {
        USER_POOL.put(userName, nickName);
    }

    public static void remove(String userName) {
        USER_POOL.remove(userName);
    }

    public static JSONObject get(String userName) {
        return USER_POOL.get(userName);
    }

	public static Map<String, JSONObject> getUserPool() {
		return USER_POOL;
	}

    
}