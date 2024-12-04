package com.example.servlet;


import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
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

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter writer = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String gender=req.getParameter("gender");

        System.out.println(username);
        System.out.println(password);
        System.out.println(gender);

        UserService userService=new UserServiceImpl();
        User user=new User(username,password,gender);
        boolean b=userService.saveUser(user);
        if(b){
            resp.setHeader("refresh", "3;url=html/login.html");
            writer.println("注册成功!!!<a href='html/login.html'>点击跳转到登录页面</a>");


        }else{
            writer.println("当前网路比较波动，请稍后再试！");
            resp.sendRedirect("demo/html/reg.html");
        }
    }

//    @Override
//    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
//        res.setContentType("text/html;charset=utf-8");
//
//        PrintWriter writer = res.getWriter();
//
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String gender=req.getParameter("gender");
//
//        System.out.println(username);
//        System.out.println(password);
//        System.out.println(gender);
//
//        UserService userService=new UserServiceImpl();
//        User user=new User(username,password,gender);
//        boolean b=userService.saveUser(user);
//        if(b){
//            writer.println("注册成功");
//
//        }else{
//            writer.println("当前网路比较波动，请稍后再试！");
//        }
//    }
}
