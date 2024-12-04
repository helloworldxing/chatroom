package test.com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.model.User;
import com.example.service.ChatServer;
import com.example.service.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserDao userDao;
    User user = new User();

    @Before
    public void setUp() {
        userService = new UserServiceImpl();
        userDao = new UserDaoImpl();

    }

    @Test
    public void testSaveUser() {
        // 创建一个测试用户
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // 调用saveUser方法
        boolean result = userService.saveUser(user);

        // 验证用户是否被成功保存
        assertTrue("用户保存失败", result);

        // 验证数据库中是否存在该用户
        User savedUser = userDao.queryUserByUsername("testUser");
        assertNotNull("数据库中未找到用户", savedUser);
    }

    @Test
    public void testLogin_Success() throws NoSuchAlgorithmException {
        // Arrange
        String username = "111";
        String password = "111";
        MessageDigest instance= MessageDigest.getInstance("md5");
        byte[] bytes = instance.digest(password.getBytes());
        String pwd= new String(bytes);

        user.setUsername(username);
        user.setPassword(pwd);

        userDao.addUser(user); // 假设 userDao.addUser() 是一个有效的方法
        userService.saveUser(user);

        User loggedUser = userService.login(username, password);

        System.out.println("loggedUser: " + userService.login(username, password));
        // Assert
        assertNotNull("登录失败，返回的用户为 null", loggedUser);
    }

}