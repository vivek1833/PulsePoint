package com.PulsePoint.PulsePoint.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.Dao.UserDao;
import com.PulsePoint.PulsePoint.Models.Users;

@Service
public class UserService {

    @Autowired
    private UserDao dao;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private EmailService emailService;

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setType("STAFF");
        emailService.sendEmail(user.getEmail(), "Welcome to PulsePoint", "Welcome to PulsePoint, Please login to the system. Thanks for registering.");
        return dao.save(user);
    }
}