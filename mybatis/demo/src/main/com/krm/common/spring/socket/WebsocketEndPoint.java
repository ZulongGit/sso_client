package com.krm.common.spring.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebsocketEndPoint implements WebSocketHandler {

	/*@Override  
    protected void handleTextMessage(WebSocketSession session,  
            TextMessage message) throws Exception {  
        super.handleTextMessage(session, message);  
        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");  
        session.sendMessage(returnMessage);  
    }  */
	
	@Override  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
        System.out.println("connect to the websocket success......");  
        session.sendMessage(new TextMessage("Server:connected OK!"));  
    }  
  
    @Override  
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {  
        TextMessage returnMessage = new TextMessage(wsm.getPayload()  
                + " received at server");  
        System.out.println(wss.getHandshakeHeaders().getFirst("Cookie"));  
        wss.sendMessage(returnMessage);  
    }  
  
    @Override
    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {  
        if(wss.isOpen()){  
            wss.close();  
        }  
       System.out.println("websocket connection closed......");  
    }  
  
    @Override  
    public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {  
        System.out.println("websocket connection closed......");  
    }  
  
    @Override  
    public boolean supportsPartialMessages() {  
        return false;  
    }
}
