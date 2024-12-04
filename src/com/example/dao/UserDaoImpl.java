package com.example.dao;

import com.example.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao{
    @Override
    public int addUser(User user) {
        Connection connection=null;
        PreparedStatement ps=null;
        int i=-1;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/big_event?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","123456");
            String sql="insert into hp (username,password,gender) values(?,?,?)";
            ps= connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getGender());
            i= ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return  -1;
        }finally {
            System.out.println(i);
            if (ps!=null)
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (connection!=null){
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return  i;
    }

    @Override
    public User queryUserByUsername(String username) {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        User user=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/big_event?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","123456");
            String sql="select username,password,gender from hp where username=?";
            ps= connection.prepareStatement(sql);
            ps.setString(1,username);
            rs=ps.executeQuery();
            if (rs.next()) {
                String username2= rs.getString("username");
                String password= rs.getString("password");
                String gender= rs.getString("gender");
                user=new User(username2,password,gender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rs!=null)
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (ps!=null)
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (connection!=null){
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return  user;
    }

    @Override
    public boolean isUserExist(String username) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/big_event?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","123456");
            String sql = "select username from hp where username=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

