package test.com.example.dao;

import com.example.dao.UserDaoImpl;
import com.example.model.User;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    private UserDaoImpl userDao;

    // 在每个测试方法执行之前执行，初始化测试所需的资源
    @Before
    public void setUp() {
        userDao = new UserDaoImpl();
    }

    // 在每个测试方法执行之后执行，清理测试过程中插入的数据，确保不影响其他测试
    @After
    public void tearDown() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/big_event?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
             PreparedStatement ps = connection.prepareStatement("DELETE FROM hp WHERE username = ?")) {
            ps.setString(1, "testUser"); // 删除用户名为 "testUser" 的测试数据
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 处理可能的异常
        }
    }

    // 测试添加一个有效的用户
    @Test
    public void testAddUser() {
        User user = new User("testUser", "password123", "女");
        int result = userDao.addUser(user);
        assertEquals(1, result); // 验证返回值为 1，表示添加成功

        // 验证数据库中确实插入了该用户
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/big_event?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM hp WHERE username = ?")) {
            ps.setString(1, "testUser");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next()); // 确保查询到用户数据
                assertEquals("testUser", rs.getString("username"));
                assertEquals("password123", rs.getString("password"));
                assertEquals("女", rs.getString("gender"));
            }
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // 测试添加一个包含空字段的用户
    @Test
    public void testAddUserWithNullFields() {
        User user = new User(null, "", "");
        int result = userDao.addUser(user);
        assertEquals(0, result); // 假设返回 0 表示无效数据
    }

    // 测试添加用户名重复的用户
    @Test
    public void testAddUserWithDuplicateUsername() {
        User user = new User("testUser", "password123", "女");
        userDao.addUser(user);

        // 尝试添加同样的用户名
        User duplicateUser = new User("testUser", "password1234", "男");
        int result = userDao.addUser(duplicateUser);
        assertEquals(0, result); // 假设返回 0 表示用户名已存在
    }

    // 测试通过用户名查询用户
    @Test
    public void testQueryUserByUsername() {
        User user = new User("testUser", "password123", "女");
        userDao.addUser(user);

        User queriedUser = userDao.queryUserByUsername("testUser");
        assertNotNull(queriedUser); // 确保查询结果不为空
        assertEquals("testUser", queriedUser.getUsername());
        assertEquals("password123", queriedUser.getPassword());
        assertEquals("女", queriedUser.getGender());
    }

    // 测试查询不存在的用户
    @Test
    public void testQueryNonExistentUser() {
        User queriedUser = userDao.queryUserByUsername("nonExistentUser");
        assertNull(queriedUser); // 验证查询结果为空，表示该用户不存在
    }

    // 测试判断用户是否存在
    @Test
    public void testIsUserExist() {
        User user = new User("testUser", "password123", "女");
        userDao.addUser(user);

        boolean exists = userDao.isUserExist("testUser");
        assertTrue(exists); // 验证用户存在

        exists = userDao.isUserExist("nonExistentUser");
        assertFalse(exists); // 验证用户不存在
    }

    // 测试判断用户名为 null 时，用户是否存在
    @Test
    public void testIsUserExistWithNullUsername() {
        boolean exists = userDao.isUserExist(null);
        assertFalse(exists); // 假设传入 null 时应该返回 false
    }

    // 测试数据库连接错误
    @Test(expected = SQLException.class)
    public void testDatabaseConnectionError() {
        // 使用错误的数据库 URL 模拟连接失败
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invalid_db", "root", "wrongPassword")) {
            assertNull(connection); // 连接应该为空
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // 测试添加包含非法数据的用户（如用户名包含特殊字符）
    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithInvalidData() {
        User invalidUser = new User("$$$InvalidUser$$$", "password123", "女");
        userDao.addUser(invalidUser); // 应该抛出非法数据异常
    }

    // 测试添加用户名为空的用户
    @Test
    public void testAddUserWithEmptyUsername() {
        User user = new User("", "password123", "男");
        int result = userDao.addUser(user);
        assertEquals(0, result); // 假设用户名为空时返回 0
    }
}

