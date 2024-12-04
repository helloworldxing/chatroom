package com.example.service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/test2/private/{username}/{targetUser}")
public class PrivateChatServer {

    private static Map<String, Session> userMap = new HashMap<>();

    @OnOpen
    public void onOpen(@PathParam("username") String username, @PathParam("targetUser") String targetUser, Session session) throws IOException {
        System.out.println("私聊用户上线啦......" + username);
        userMap.put(username, session);

        // 可以发送一些初始化信息给用户，比如在线用户列表等
    }

    @OnMessage
    public void onMessage(String message, @PathParam("targetUser") String targetUser) throws Exception {
        Session targetSession = userMap.get(targetUser);
        if (targetSession != null) {
            targetSession.getBasicRemote().sendText(message);
        } else {
            // 处理目标用户不在线的情况
        }
    }

    @OnClose
    public void onClose(@PathParam("username") String username) {
        System.out.println("私聊用户下线啦......");
        userMap.remove(username);
    }
}
