package com.PulsePoint.PulsePoint.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Services.UserService;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public String register(@RequestBody Users user) {
        try {
            Users response = userService.register(user);
            return response.getUsername();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}