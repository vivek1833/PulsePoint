package com.PulsePoint.PulsePoint.service.impl;

import java.sql.Date;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.PulsePoint.PulsePoint.dto.otpDTO;
import com.PulsePoint.PulsePoint.model.Users;
import com.PulsePoint.PulsePoint.repository.UserRepo;
import com.PulsePoint.PulsePoint.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo repo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final RedisService redisService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepo repo, AuthenticationManager authenticationManager, JwtService jwtService, EmailService emailService, RedisService redisService) {
        this.repo = repo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.redisService = redisService;
    }

    @Override
    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(100000, 999999));
        redisService.saveOtp(email, otp, 5);
        emailService.publishEmailEvent(email, "Registration on PulsePoint",
                "Thanks for registering on PulsePoint. Your OTP for PulsePoint is " + otp
                        + ". This OTP will expire in 5 minutes. Please verify your account within 5 minutes.");
    }

    @Override
    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setType("STAFF");
        sendOtp(user.getEmail());
        Users savedUser = repo.save(user);
        savedUser.setUserName(user.getUserName());
        savedUser.setActive(false);
        savedUser.setIsLoggedIn(false);
        savedUser.setCreatedAt(new Date(System.currentTimeMillis()));
        savedUser.setUpdatedAt(new Date(System.currentTimeMillis()));
        return savedUser;
    }

    @Override
    public String login(Users user) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUserName());
            Users loggedInUser = repo.findByUserName(user.getUserName());
            loggedInUser.setIsLoggedIn(true);
            repo.save(loggedInUser);
            return token;
        }
        return null;
    }

    @Override
    public String verifyOtp(otpDTO otpDTO) {
        String redisOtp = redisService.getOtp(otpDTO.getEmail());
        if (redisOtp == null) {
            return "OTP expired";
        }
        if (otpDTO.getOtp().equals(redisOtp)) {
            redisService.deleteOtp(otpDTO.getEmail());
            Users user = repo.findByUserName(otpDTO.getEmail());
            user.setActive(true);
            repo.save(user);
            return "OTP verified";
        }
        return "Invalid OTP";
    }

    @Override
    public String resendOtp(otpDTO otpDTO) {
        String redisOtp = redisService.getOtp(otpDTO.getEmail());
        if (redisOtp != null) {
            redisService.deleteOtp(otpDTO.getEmail());
        }
        String newOtp = String.valueOf(new Random().nextInt(100000, 999999));
        redisService.saveOtp(otpDTO.getEmail(), newOtp, 5);
        emailService.publishEmailEvent(otpDTO.getEmail(), "Registration on PulsePoint",
                "Thanks for registering on PulsePoint. Your OTP for PulsePoint is " + newOtp
                        + ". This OTP will expire in 5 minutes. Please verify your account within 5 minutes.");
        return "OTP resent";
    }

    @Override
    public Users getLoggedInUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = repo.findByUserName(username);
        return user;
    }

    @Override
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = repo.findByUserName(username);
        user.setIsLoggedIn(false);
        repo.save(user);
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        return "Logged out";
    }

    @Override
    public Users update(Users user) {
        Users existing = repo.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getUserName() != null) {
            existing.setUserName(user.getUserName());
        }
        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }
        if (user.getType() != null) {
            existing.setType(user.getType());
        }
        if (user.getPassword() != null) {
            existing.setPassword(encoder.encode(user.getPassword()));
        }
        if (user.getCreatedAt() != null) {
            existing.setCreatedAt(user.getCreatedAt());
        }
        existing.setUpdatedAt(new Date(System.currentTimeMillis()));
        existing.setIsLoggedIn(true);
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        return repo.save(existing);
    }
}