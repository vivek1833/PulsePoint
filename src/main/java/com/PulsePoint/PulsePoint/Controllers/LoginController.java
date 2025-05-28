package com.PulsePoint.PulsePoint.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Services.JwtService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Login Failed";
    }
}
