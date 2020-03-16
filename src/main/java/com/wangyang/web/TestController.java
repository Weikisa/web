package com.wangyang.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wangyang.web.Model.User;
import com.wangyang.web.Service.UserService;

@RestController
public class TestController {
	@Autowired
    private UserService userService;
	
	@GetMapping("/test/{id}")
    public User findUserByID(@PathVariable("id") Long id){
        
        
        return userService.findUserById(id);
    }
	
	
}
