package com.PulsePoint.PulsePoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class otpDTO {
    private String email;
    private String otp;
} 