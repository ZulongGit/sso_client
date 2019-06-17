package com.krm.web.webSocket.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.krm.web.webSocket.client.SoketClient;
import com.krm.web.webSocket.common.SessionPool;
import com.krm.web.webSocket.common.UserPool;
import com.krm.web.webSocket.model.Message;

public class MessageUtil {

	private static Logger logger = LoggerFactory.getLogger(SoketClient.class);

	public static void send(Message message, Session session) throws Exception {
		message.setUserName(session.getUserPrincipal().getName());
		if (message.getType() != null) {
			if (message.getType().equals("1")) {
				JSONObject obj = new JSONObject();
				obj.put("userName", message.getUserName());
				obj.put("isAdmin", message.getIsAdmin());
				UserPool.add(message.getUserName(), obj);
				onlineUser();
			}else if (message.getType().equals("2")) {
				UserPool.remove(session.getUserPrincipal().getName());	//移除用户池
				onlineUser();
			}else if (message.getType().equals("3")) {
				sendMessage(message, session);
			}else if (message.getType().equals("4")) {
				List<Session> list = SessionPool.getSessionByUserName(message.getSendTo());
    			if(list != null && list.size() != 0){
    				//发送退出命令
    				for (Session savedSession : list) {
    					goOut(message, savedSession);
					}
    			}
			}else if (message.getType().equals("5")) {
				onlineUser();
			}
		}
	}
	
	public static void sendMessage(Message message, Session session) throws Exception {
		// 给所有用户发送消息
		if (message.getScope().equals("all")) {
			Set<Session> keys = SessionPool.getHttpSessionIdPool().keySet();
			for (Session key : keys) {
				// 屏蔽状态关闭的用户
				// if(!session.isOpen()) {
				// UserPool.remove(session.getId());
				// continue;
				// }
				// 排除自己
				if (key.equals(session)) {
					continue;
				}
				try {
					// 发送消息
					key.getBasicRemote().sendText(JSONObject.toJSONString(message));
				} catch (IOException e) {
					logger.error("给用户 [" + session.getId() + "] 发送消息失败", e);
				}
			}
		}
	}
	
	
	public static void goOut(Message message, Session session) throws Exception {
		logger.info("开始强制用户退出");
		if(session.isOpen()){
			session.getBasicRemote().sendText(JSONObject.toJSONString(message));
		}
//		List<Session> list = SessionPool.getSessionByHttpId(session);
//		if(list != null && list.size() != 0){
////			发送退出命令
//			for (Session savedSession : list) {
//				if(savedSession != session){
////					UserPool.remove(message.getSendTo());	//移除用户池
//				}
//			}
//		}
		onlineUser();
	}
	
	
	public static void onlineUser() throws Exception {
		logger.info("开始获取在线用户");
		Message message = new Message();
		message.setType("5");
    	List<Map<String,Object>> list = Lists.newArrayList();
    	int i = 1;
    	Set<String> users = UserPool.getUserPool().keySet();
    	for (String user : users) {
    		JSONObject temp = UserPool.get(user);
    		temp.put("id", i);
    		list.add(temp);
    		i++;
    	}
    	message.setContent(JSONArray.toJSONString(list));
    	Set<Session> sessions = SessionPool.getHttpSessionIdPool().keySet();
    	for (Session key : sessions) {
    		key.getBasicRemote().sendText(JSONObject.toJSONString(message));
		}
	}
}
