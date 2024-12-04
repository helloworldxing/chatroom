package com.example.dao;

import com.example.model.User;

public interface UserDao {
    int addUser(User user);
    User queryUserByUsername(String username);
    boolean isUserExist(String username);
}
