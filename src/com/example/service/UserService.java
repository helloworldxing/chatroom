package com.example.service;

import com.example.model.User;

public interface UserService {
    boolean saveUser(User user);
    User login(String username, String password);
}
