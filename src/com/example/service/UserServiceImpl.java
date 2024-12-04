package com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService{


    @Override
    public boolean saveUser(User user){

        String password = user.getPassword();
        try {
            MessageDigest instance= MessageDigest.getInstance("md5");
            byte[] bytes = instance.digest(password.getBytes());
            String pwd= new String(bytes);

            user.setPassword(pwd);


        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        UserDao userDao = new UserDaoImpl();
        int i=userDao.addUser(user);
        if(i!=-1){
            return  true;
        }
        return  false;
    }

    @Override
    public User login(String username, String password) {
        UserDao userDao= new UserDaoImpl();
        User user = userDao.queryUserByUsername(username);
        if(user!=null){
            String pwd=user.getPassword();
            try {
                MessageDigest instance= MessageDigest.getInstance("md5");
                byte[] bytes = instance.digest(password.getBytes());
                password= new String(bytes);
                if(!password.equals(pwd)){
                    System.out.println("password: "+password);
                    return user;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
