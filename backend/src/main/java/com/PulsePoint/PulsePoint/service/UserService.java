package com.PulsePoint.PulsePoint.service;

import com.PulsePoint.PulsePoint.model.Users;
import com.PulsePoint.PulsePoint.dto.otpDTO;

public interface UserService {
    void sendOtp(String email);

    Users register(Users user);

    String login(Users user);

    String verifyOtp(otpDTO otpDTO);

    String resendOtp(otpDTO otpDTO);

    Users getLoggedInUserDetails();

    String logout();

    Users update(Users user);
}