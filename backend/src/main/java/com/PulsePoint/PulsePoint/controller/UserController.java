package com.PulsePoint.PulsePoint.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.dto.otpDTO;
import com.PulsePoint.PulsePoint.model.Users;
import com.PulsePoint.PulsePoint.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @GetMapping("/")
    public ResponseEntity<?> getLoggedInUserDetails() {
        Users response = userService.getLoggedInUserDetails();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        String response = userService.logout();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    @PostMapping("/")
    public ResponseEntity<?> update(@RequestBody Users user) {
        try {
            Users response = userService.update(user);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + e.getMessage());
        }
    }
}
