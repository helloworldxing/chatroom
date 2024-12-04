package com.example.servlet;

import com.example.model.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserService userService = new UserServiceImpl();
        User user= userService.login(username, password);
        PrintWriter writer = resp.getWriter();
        if(user!=null){
            resp.sendRedirect("/demo/html/chat_room.html?username="+username);
            writer.println("登录成功,欢迎:"+user.getUsername()+"登录");
        }else{
            writer.println("您输入的用户名或密码有误，请重新输入！");
            resp.sendRedirect("/demo/html/login.html");

        }
    }

//    @Override
//    public  void service(ServletRequest req, ServletResponse res) throws IOException {
//        res.setContentType("text/html;charset=utf-8");
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        UserService userService = new UserServiceImpl();
//        User user= userService.login(username, password);
//        PrintWriter writer = res.getWriter();
//        if(user!=null){
//            writer.println("登录成功,欢迎:"+user.getUsername()+"登录");
//        }else{
//
//            writer.println("您输入的用户名或密码有误，请重新输入！");
//        }
//    }
}
