package com.sjtu.project.logservice.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static Map<String, List<String>> processMap = new ConcurrentHashMap<>();

    /**
     * webSocket连接创建后调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 获取参数
        String processId = String.valueOf(session.getAttributes().get("processId"));
        if (!processMap.containsKey(processId)){
            List<String> sessionIds = new ArrayList<>();
            sessionIds.add(session.getId());
            processMap.put(processId, sessionIds);
        }
        else {
            List<String> sessionIds = processMap.get(processId);
            sessionIds.add(session.getId());
            processMap.put(processId, sessionIds);
        }
        sessionMap.put(session.getId(), session);
    }

    /**
     * 接收到消息会调用
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("Unexpected WebSocket message type: " + message);
    }

    /**
     * 连接出错会调用
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        deleteSession(session);
    }

    /**
     * 连接关闭会调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        deleteSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void deleteSession(WebSocketSession session){
        sessionMap.remove(session.getId());
        String processId = String.valueOf(session.getAttributes().get("processId"));
        List<String> sessionIds = processMap.get(processId);
        sessionIds.remove(session.getId());
        if (sessionIds.size() == 0){
            processMap.remove(processId);
        }
        else {
            processMap.put(processId, sessionIds);
        }
    }

    /**
     * 后端发送消息
     */
    public void sendMessage(String processId, String message) throws IOException {
        if (!processMap.containsKey(processId) || processMap.get(processId).size() == 0){
            return;
        }
        List<String> sessionIds = processMap.get(processId);
        for (String sessionId : sessionIds){
            WebSocketSession session = sessionMap.get(sessionId);
            session.sendMessage(new TextMessage(message));
        }
    }
}
