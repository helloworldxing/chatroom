package com.example.dao;

import com.example.dao.UserDaoImpl;
import com.example.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    private UserDaoImpl userDao;

    @Before
    public void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    public void testAddUser() {
        User user = new User("testUser", "password123", "male");
        int result = userDao.addUser(user);
        assertEquals(1, result);
    }

    @Test
    public void testQueryUserByUsername() {
        User user = userDao.queryUserByUsername("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testIsUserExist() {
        boolean exists = userDao.isUserExist("testUser");
        assertTrue(exists);
    }
}