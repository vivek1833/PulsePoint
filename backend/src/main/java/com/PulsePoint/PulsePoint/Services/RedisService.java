package com.PulsePoint.PulsePoint.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveOtp(String email, String otp, long timeoutMinutes) {
        System.out.println("Saving OTP for " + email + " with OTP: " + otp);
        redisTemplate.opsForValue().set("OTP::" + email, otp, timeoutMinutes, TimeUnit.MINUTES);
    }

    public String getOtp(String email) {
        return redisTemplate.opsForValue().get("OTP::" + email);
    }

    public void deleteOtp(String email) {
        redisTemplate.delete("OTP::" + email);
    } 
}
