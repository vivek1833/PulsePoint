package com.PulsePoint.PulsePoint.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Services.UserService;

import com.PulsePoint.PulsePoint.Repo.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    EmailService emailService;

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setType("STAFF");
        emailService.sendEmail(user.getEmail(), "Welcome to PulsePoint",
                "Welcome to PulsePoint, Please login to the system. Thanks for registering.");
        return repo.save(user);
    }

    public String login(Users user) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return null;
    }
}