package test1.com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.model.User;
import com.example.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class UserServiceBlackTest {

    private UserServiceImpl userService;
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = new UserDaoImpl();
        userService = new UserServiceImpl();
    }

    @Test
    public void testSaveUser() throws NoSuchAlgorithmException {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Act
        boolean result = userService.saveUser(user);

        // Assert
        assertTrue("用户保存成功", result);

        MessageDigest instance = MessageDigest.getInstance("md5");
        byte[] bytes = instance.digest("testPassword".getBytes());
        String expectedPassword = new String(bytes);
        assertEquals("密码不匹配", expectedPassword, user.getPassword());

        User savedUser = userDao.queryUserByUsername("testUser");
        assertNotNull("用户不为空", savedUser);
        assertEquals("用户名不匹配", "testUser", savedUser.getUsername());
        assertEquals("密码不匹配", expectedPassword, user.getPassword());
    }

    @Test
    public void testSaveUser_ExceptionHandling() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(null); // This will cause an exception

        // Act
        boolean result = userService.saveUser(user);

        // Assert
        assertFalse("User should not be saved due to exception", result);
    }

    public static class UserServiceImplBlackBoxTest {

        private UserServiceImpl userService;
        private UserDao userDao;

        @Before
        public void setUp() {
            userService = new UserServiceImpl();
            userDao = new UserDaoImpl();
        }

        @Test
        public void testLogin_SuccessBlack() throws NoSuchAlgorithmException {
            // Arrange
            String username = "testUser";
            String password = "testPassword";
            MessageDigest instance = MessageDigest.getInstance("md5");
            byte[] bytes = instance.digest(password.getBytes());
            String hashedPassword = new String(bytes);

            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPassword);
            userDao.addUser(user);

            // Act
            User loggedUser = userService.login(username, password);

            // Assert
            assertNotNull("登录失败，用户为空", loggedUser);
            assertEquals("用户名不匹配", username, loggedUser.getUsername());
        }

        @Test
        public void testLogin_Failure() {
            // Arrange
            String username = "nonExistentUser";
            String password = "wrongPassword";

            // Act
            User loggedUser = userService.login(username, password);

            // Assert
            assertNull("Login should fail, returned user should be null", loggedUser);
        }
    }
}