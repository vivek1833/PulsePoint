package com.PulsePoint.PulsePoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class otpDTO {
    private String email;
    private String otp;
}
