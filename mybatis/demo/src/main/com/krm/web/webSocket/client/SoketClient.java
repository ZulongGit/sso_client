package com.krm.web.webSocket.client;

import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.alibaba.fastjson.JSONObject;
import com.krm.web.webSocket.common.SessionPool;
import com.krm.web.webSocket.common.UserPool;
import com.krm.web.webSocket.model.Message;
import com.krm.web.webSocket.util.MessageUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@ServerEndpoint("/webSocketServer")
public class SoketClient {

    private static Logger logger = LoggerFactory.getLogger(SoketClient.class);
  //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
  	private static int onlineCount = 0;
  	
    @OnOpen
    public void onOpen(Session session) throws Exception {
    	SessionPool.add(session);		//加入用户池
    	addOnlineCount(session);    //在线数加1
    	logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String content, Session session) throws Exception {
    	JSONObject obj = JSONObject.parseObject(content);
    	Message message = JSONObject.toJavaObject(obj, Message.class);
    	MessageUtil.send(message, session);
        logger.info("用户 [" + session.getUserPrincipal().getName() + "] 接收到消息: " + content);
    }

    @OnError
    public void onError(Throwable error) {
    	logger.error("『*****websocket服务发出异常，以下是异常信息：*****』");
    	error.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        SessionPool.remove(session);	//移除用户池
        UserPool.remove(session.getUserPrincipal().getName());
		try {
			MessageUtil.onlineUser();
		} catch (Exception e) {
		}
        subOnlineCount();           //在线数减1
		logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    
    public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount(Session session) {
		int count = 0;
		String userName = session.getUserPrincipal().getName();
		Set<Session> keys = SessionPool.getHttpSessionIdPool().keySet();
    	for (Session key : keys) {
			if(key.getUserPrincipal().getName().equals(userName)){
				count++;
			}
		}
		if(count == 1){
			SoketClient.onlineCount++;
		}
	}

	public static synchronized void subOnlineCount() {
		if(SoketClient.onlineCount > 0){
			SoketClient.onlineCount--;
		}
	}
}
