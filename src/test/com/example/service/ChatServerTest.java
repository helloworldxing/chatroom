package test.com.example.service;

import com.example.service.ChatServer;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.*;
import javax.websocket.RemoteEndpoint.Basic;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.*;
import static org.junit.Assert.*;

public class ChatServerTest {

    private ChatServer chatServer;
    private Session session;
    private Map<String, Session> userMap;

    @Before
    public void setUp() {
        chatServer = new ChatServer();
        session = new TestSession();
        userMap = new HashMap<>();
    }

    @Test
    public void testOnOpen() throws IOException {
        String username = "111";
        chatServer.onOpen(username, session);
        System.out.println(chatServer.getUserMap());  // 输出 userMap 内容
        System.out.println(username); // 输出 username 值

        assertTrue(chatServer.getUserMap().containsKey(username));

    }


    @Test
    public void testOnMessage_PrivateMessage() throws Exception {
        // Arrange
        String senderUsername = "sender";
        String targetUsername = "target";
        TestSession targetSession = new TestSession();
        TestSession senderSession = new TestSession();

        userMap.put(senderUsername, senderSession);
        userMap.put(targetUsername, targetSession);

        chatServer.onOpen(senderUsername, senderSession);
        chatServer.onOpen(targetUsername, targetSession);

        String message = "@private@" + targetUsername +"@Hello";

        System.out.println("userMap: " + userMap);
        System.out.println(message);
        // Act
        chatServer.onMessage(message, senderSession);
//        chatServer.onMessage(message, targetSession);
        // Assert
        assertEquals("@" + senderUsername + " (私聊): Hello", senderSession.getLastMessage());
        assertEquals("@" + senderUsername + " (私聊): Hello", targetSession.getLastMessage());
    }


    @Test
    public void testOnMessage_GroupMessage() throws Exception {
        String message = "Hello everyone!";
        userMap.put("user1", session);

        chatServer.onOpen(message, session);

        assertEquals("@"+message, ((TestSession) session).getLastMessage());
    }

    @Test
    public void testOnClose() throws IOException {
        String username = "111";
        //userMap.put(username, session);
        chatServer.onOpen(username, session);
        chatServer.onClose(username, session);
        assertFalse(chatServer.getUserMap().containsKey(username));
    }

    @Test
    public void testGetUsername() throws IOException {
        String username = "111";
        chatServer.onOpen(username, session);
        //userMap.put(username, session);

        String result = chatServer.getUsername(session);
        assertEquals(username, result);
    }

    class TestSession implements Session {
        private String lastMessage;

        @Override
        public WebSocketContainer getContainer() {
            return null;
        }

        @Override
        public void addMessageHandler(MessageHandler messageHandler) throws IllegalStateException {

        }

        @Override
        public <T> void addMessageHandler(Class<T> aClass, MessageHandler.Whole<T> whole) {

        }

        @Override
        public <T> void addMessageHandler(Class<T> aClass, MessageHandler.Partial<T> partial) {

        }

        @Override
        public Set<MessageHandler> getMessageHandlers() {
            return Collections.emptySet();
        }

        @Override
        public void removeMessageHandler(MessageHandler messageHandler) {

        }

        @Override
        public String getProtocolVersion() {
            return "";
        }

        @Override
        public String getNegotiatedSubprotocol() {
            return "";
        }

        @Override
        public List<Extension> getNegotiatedExtensions() {
            return Collections.emptyList();
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public long getMaxIdleTimeout() {
            return 0;
        }

        @Override
        public void setMaxIdleTimeout(long l) {

        }

        @Override
        public void setMaxBinaryMessageBufferSize(int i) {

        }

        @Override
        public int getMaxBinaryMessageBufferSize() {
            return 0;
        }

        @Override
        public void setMaxTextMessageBufferSize(int i) {

        }

        @Override
        public int getMaxTextMessageBufferSize() {
            return 0;
        }

        @Override
        public RemoteEndpoint.Async getAsyncRemote() {
            return null;
        }

        @Override
        public Basic getBasicRemote() {
            return new Basic() {
                @Override
                public void setBatchingAllowed(boolean b) throws IOException {

                }

                @Override
                public boolean getBatchingAllowed() {
                    return false;
                }

                @Override
                public void flushBatch() throws IOException {

                }

                @Override
                public void sendPing(ByteBuffer byteBuffer) throws IOException, IllegalArgumentException {

                }

                @Override
                public void sendPong(ByteBuffer byteBuffer) throws IOException, IllegalArgumentException {

                }

                @Override
                public void sendText(String text) throws IOException {
                    lastMessage = text;
                }

                @Override
                public void sendBinary(ByteBuffer byteBuffer) throws IOException {

                }

                @Override
                public void sendText(String s, boolean b) throws IOException {

                }

                @Override
                public void sendBinary(ByteBuffer byteBuffer, boolean b) throws IOException {

                }

                @Override
                public OutputStream getSendStream() throws IOException {
                    return null;
                }

                @Override
                public Writer getSendWriter() throws IOException {
                    return null;
                }

                @Override
                public void sendObject(Object o) throws IOException, EncodeException {

                }
            };
        }

        @Override
        public String getId() {
            return "";
        }

        @Override
        public void close() throws IOException {

        }

        @Override
        public void close(CloseReason closeReason) throws IOException {

        }

        @Override
        public URI getRequestURI() {
            return null;
        }

        @Override
        public Map<String, List<String>> getRequestParameterMap() {
            return Collections.emptyMap();
        }

        @Override
        public String getQueryString() {
            return "";
        }

        @Override
        public Map<String, String> getPathParameters() {
            return Collections.emptyMap();
        }

        @Override
        public Map<String, Object> getUserProperties() {
            return Collections.emptyMap();
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public Set<Session> getOpenSessions() {
            return Collections.emptySet();
        }

        public String getLastMessage() {
            return lastMessage;
        }

        // Implement other methods from the Session interface as needed
    }
}