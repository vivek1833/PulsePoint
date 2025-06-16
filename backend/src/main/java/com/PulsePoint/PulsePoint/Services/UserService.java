package com.PulsePoint.PulsePoint.Services;

import java.sql.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.PulsePoint.PulsePoint.DTO.otpDTO;
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

    @Autowired
    RedisService redisService;

    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(100000, 999999));
        redisService.saveOtp(email, otp, 5);
        emailService.sendEmail(email, "Registration on PulsePoint",
                "Thanks for registering on PulsePoint. Your OTP for PulsePoint is " + otp
                        + ". This OTP will expire in 5 minutes. Please verify your account within 5 minutes.");
    }

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setType("STAFF");

        sendOtp(user.getEmail());

        Users savedUser = repo.save(user);
        savedUser.setUsername(user.getUsername());
        savedUser.setActive(false);
        savedUser.setCreatedAt(new Date(System.currentTimeMillis()));
        savedUser.setUpdatedAt(new Date(System.currentTimeMillis()));

        return savedUser;
    }

    public String login(Users user) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return null;
    }

    public String verifyOtp(otpDTO otpDTO) {
        String redisOtp = redisService.getOtp(otpDTO.getEmail());
        if (redisOtp == null) {
            return "OTP expired";
        }
        if (otpDTO.getOtp().equals(redisOtp)) {
            redisService.deleteOtp(otpDTO.getEmail());
            Users user = repo.findByUsername(otpDTO.getEmail());
            user.setActive(true);
            repo.save(user);
            return "OTP verified";
        }

        return "Invalid OTP";
    }

    public String resendOtp(otpDTO otpDTO) {
        String redisOtp = redisService.getOtp(otpDTO.getEmail());
        if (redisOtp != null) {
            redisService.deleteOtp(otpDTO.getEmail());
        }
        String newOtp = String.valueOf(new Random().nextInt(100000, 999999));
        redisService.saveOtp(otpDTO.getEmail(), newOtp, 5);
        sendOtp(otpDTO.getEmail());
        return "OTP resent";
    }
}