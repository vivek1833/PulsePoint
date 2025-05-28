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

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setType("STAFF");
        return dao.save(user);
    }
}