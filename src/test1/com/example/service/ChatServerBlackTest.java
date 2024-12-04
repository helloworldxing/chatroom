package test1.com.example.service;

import com.example.service.ChatServer;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ChatServerBlackTest {

    private ChatServer chatServer;
    private Session session;
    private Session session1;
    private Session session2;

    @Before
    public void setUp() {
        chatServer = new ChatServer();
        session = new TestSession();
        session1 = new TestSession();
        session2 = new TestSession();
    }

    @Test
    public void testOnOpenBlack() throws IOException {
        // Arrange
        String username = "testUser";

        // Act
        chatServer.onOpen(username, session);

        // Assert
        Map<String, Session> userMap = chatServer.getUserMap();
        assertTrue("User map包含新用户", userMap.containsKey(username));
        assertEquals("Session匹配", session, userMap.get(username));
    }
    @Test
    public void testOnCloseBlack() throws IOException {
        // Arrange
        String username = "testUser";

        // Act
        chatServer.onOpen(username, session);
        chatServer.onClose(username, session);

        // Assert
        Map<String, Session> userMap = chatServer.getUserMap();
        assertFalse("User map不包含新用户了", userMap.containsKey(username));
    }

    @Test
    public void testOnMessage_GroupMessage_Black() throws Exception {
        // Arrange
        String username1 = "user1";
        String username2 = "user2";
        String message = "Hello everyone!";

        chatServer.onOpen(username1, session1);
        chatServer.onOpen(username2, session2);

        // Act
        chatServer.onMessage(message, session1);

        // Assert
        assertEquals("Hello everyone!", ((TestSession) session1).getLastMessage());
        assertEquals("Hello everyone!", ((TestSession) session2).getLastMessage());
    }


    @Test
    public void testGetUsername_NonExistingUser_Black() {
        // Act
        String result = chatServer.getUsername(session2);

        // Assert
        assertEquals("用户不存在返回'Unknown'", "Unknown", result);
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
            return null;
        }

        @Override
        public void removeMessageHandler(MessageHandler messageHandler) {

        }

        @Override
        public String getProtocolVersion() {
            return null;
        }

        @Override
        public String getNegotiatedSubprotocol() {
            return null;
        }

        @Override
        public List<Extension> getNegotiatedExtensions() {
            return null;
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
        public RemoteEndpoint.Basic getBasicRemote() {
            return new RemoteEndpoint.Basic() {
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
            return null;
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
            return null;
        }

        @Override
        public String getQueryString() {
            return null;
        }

        @Override
        public Map<String, String> getPathParameters() {
            return null;
        }

        @Override
        public Map<String, Object> getUserProperties() {
            return null;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public Set<Session> getOpenSessions() {
            return null;
        }

        public String getLastMessage() {
            return lastMessage;
        }
    }
}