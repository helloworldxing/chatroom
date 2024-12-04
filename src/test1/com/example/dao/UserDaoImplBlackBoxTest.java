package test1.com.example.dao;

import com.example.dao.UserDaoImpl;
import com.example.model.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class UserDaoImplBlackBoxTest {

    private UserDaoImpl userDao;

    @Before
    public void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    public void testAddUser() {
        User user = new User("testUser", "password123", "女");
        int result = userDao.addUser(user);
        assertEquals(1, result);

        // Verify the user is added to the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/big_event?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM hp WHERE username = ?")) {
            ps.setString(1, "testUser");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals("testUser", rs.getString("username"));
                assertEquals("password123", rs.getString("password"));
                assertEquals("女", rs.getString("gender"));
            }
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testQueryUserByUsername() {
        User user = new User("testUser", "password123", "女");
        userDao.addUser(user);

        User queriedUser = userDao.queryUserByUsername("testUser");
        assertNotNull(queriedUser);
        assertEquals("testUser", queriedUser.getUsername());
        assertEquals("password123", queriedUser.getPassword());
        assertEquals("女", queriedUser.getGender());
    }

    @Test
    public void testIsUserExist() {
        User user = new User("testUser", "password123", "女");
        userDao.addUser(user);

        boolean exists = userDao.isUserExist("testUser");
        assertTrue(exists);

        exists = userDao.isUserExist("nonExistentUser");
        assertFalse(exists);
    }
}