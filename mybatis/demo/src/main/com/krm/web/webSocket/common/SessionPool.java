package com.krm.web.webSocket.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.apache.tomcat.websocket.WsSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.krm.common.utils.ServerUtil;
import com.krm.web.webSocket.model.Message;
import com.krm.web.webSocket.util.MessageUtil;

/**
 * 说明：session池
 * @author Parker
 *
 */
public class SessionPool {
    private static Logger logger = LoggerFactory.getLogger(SessionPool.class);

    //<session主键,session>
    private static Map<String, Session> SESSION_POOL = new HashMap<String, Session>();
    //<session,httpSessionId>
    private static Map<Session, String> HTTP_SESSIONID_POOL = new HashMap<Session, String>();

    public static void add(Session session) throws Exception {
    	String userName = session.getUserPrincipal().getName();
    	if(ServerUtil.isTomcat()){
    		WsSession httpSession = (WsSession) session;
    		String httpSessionId = httpSession.getHttpSessionId();
    		//空则为新登录用户
    		if(!HTTP_SESSIONID_POOL.containsValue(httpSessionId)){
    			SESSION_POOL.put(session.getId(), session);
    			//该用户已存在，则是二次登录，这样踢出旧用户
    			List<Session> list = getSessionByUserName(userName);
    			if(list != null && list.size() != 0){
    				//发送退出命令
    				for (Session savedSession : list) {
    					Message message = new Message(userName, "您的账号在其他地方登录，请点击后重新登录！", "4");
    					MessageUtil.send(message, savedSession);
					}
    			}
    			HTTP_SESSIONID_POOL.put(session, httpSessionId);
    		}else{
    			SESSION_POOL.put(session.getId(), session);
    			HTTP_SESSIONID_POOL.put(session, httpSessionId);
    		}
    	}
        logger.info("socket用户【" + userName + "】连接成功");
    }

    public static void remove(Session session) {
        SESSION_POOL.remove(session.getId());
        HTTP_SESSIONID_POOL.remove(session);
        logger.info("socket用户【" + session.getUserPrincipal().getName() + "】关闭");
    }

    /**
     * 通过httpSessionId获取session
     * @param httpSessionId
     * @return
     */
    public static Session getSession(String httpSessionId) {
        return SESSION_POOL.get(httpSessionId);
    }
    
    /**
     * 通过用户名获取session
     * @param userName
     * @return
     */
    public static List<Session> getSessionByUserName(String userName) {
    	List<Session> list = Lists.newArrayList();
    	Set<Session> keys = HTTP_SESSIONID_POOL.keySet();
    	for (Session key : keys) {
    		if(key.getUserPrincipal().getName().equals(userName)){
    			list.add(key);
    		}
    	}
    	return list;
    }
    
    /**
     * 通过用户名获取session
     * @param userName
     * @return
     */
    public static List<Session> getSessionByHttpId(Session session) {
    	List<Session> list = Lists.newArrayList();
    	Set<Session> keys = HTTP_SESSIONID_POOL.keySet();
    	if(ServerUtil.isTomcat()){
    		WsSession httpSession = (WsSession) session;
    		String httpSessionId = httpSession.getHttpSessionId();
    		for (Session key : keys) {
    			if(HTTP_SESSIONID_POOL.get(key).equals(httpSessionId)){
    				list.add(key);
    			}
    		}
    	}
    	return list;
    }
    
    /**
     * 获取httpSessionId
     * @param session
     * @return
     */
    public static String getHttpSessionId(Session session) {
    	Set<String> keys = SESSION_POOL.keySet();
    	for (String key : keys) {
			if(getSession(key) == session){
				return key;
			}
		}
    	return null;
    }

    public static Map<String, Session> getSessionPool() {
        return SESSION_POOL;
    }

	public static Map<Session, String> getHttpSessionIdPool() {
		return HTTP_SESSIONID_POOL;
	}
    
    
}
