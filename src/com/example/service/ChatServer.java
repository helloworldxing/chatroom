package com.example.service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint("/test2/{username}")
public class ChatServer {

    private static Map<String, Session> userMap = new HashMap<>();

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {
        System.out.println("有用户上线啦......" + username);
        userMap.put(username, session);
        broadcastOnlineUsers();
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        if (message.startsWith("@private@")) {
            // 私聊消息处理
            String[] parts = message.split("@", 4);
            String targetUser = parts[2];
            //String targetUser = parts[2];
            String privateMessage = parts[3];
            System.out.println("privateMessage:"+privateMessage);
            Session targetSession = userMap.get(targetUser);
            System.out.println("targetSession: " + targetSession);
            if (targetSession != null) {
                String senderUsername = getUsername(session);
                targetSession.getBasicRemote().sendText("@" + senderUsername + " (私聊): " + privateMessage);
                // 同时发送消息给发送者，让发送者能够看到自己发送的私聊消息
                session.getBasicRemote().sendText("@" + senderUsername + " (私聊): " + privateMessage);
                // 发送特殊消息更新在线用户列表
                broadcastOnlineUsers();
            }
        } else {
            // 群聊消息处理
            for (Session s : userMap.values()) {
                s.getBasicRemote().sendText(message);
            }
        }
    }

    public static String getUsername(Session session) {
        for (Map.Entry<String, Session> entry : userMap.entrySet()) {
            if (entry.getValue().equals(session)) {
                return entry.getKey();
            }
        }
        return "Unknown"; // 如果找不到对应的用户名，可以返回一个默认值
    }

    public Map<String, Session> getUserMap() {
        return userMap;
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        System.out.println("有用户下线啦......");
        userMap.remove(username);
        broadcastOnlineUsers();
    }

    private void broadcastOnlineUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        for (String s : userMap.keySet()) {
            //这里有改动
            sb.append(s);
        }
        for (Session s : userMap.values()) {
            try {
                s.getBasicRemote().sendText(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
