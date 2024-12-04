package test.com.example.servlet;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.service.UserServiceImpl;
import com.example.servlet.LoginServlet;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class LoginServletTest {
    private LoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserServiceImpl userService;
    private UserDao userDao;

    @Before
    public void setUp() {
        loginServlet = new LoginServlet();
        userService = new UserServiceImpl();
        // Initialize the database and add a test user
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword"); // Ensure this is the hashed password
        userService.saveUser(user);
    }


}