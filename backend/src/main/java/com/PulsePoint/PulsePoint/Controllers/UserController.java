package com.PulsePoint.PulsePoint.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.DTO.otpDTO;
import com.PulsePoint.PulsePoint.Models.Users;
import com.PulsePoint.PulsePoint.Services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        Users response = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        String response = userService.login(user);
        return ResponseEntity.status(HttpStatus.OK).body(response); 
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody otpDTO otpDTO) {
        String response = userService.verifyOtp(otpDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody otpDTO otpDTO) {
        String response = userService.resendOtp(otpDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getLoggedInUserDetails() {
        Users response = userService.getLoggedInUserDetails();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        String response = userService.logout();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
