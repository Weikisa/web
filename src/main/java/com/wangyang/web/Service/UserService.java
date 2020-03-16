package com.wangyang.web.Service;

import java.util.List;

import com.wangyang.web.Model.User;

public interface UserService {
    User checkUser(String username, String password);
    
    User saveUser(User user);
    
    public User findUserById(Long id);
}
