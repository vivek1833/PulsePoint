package com.PulsePoint.PulsePoint.service.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveOtp(String email, String otp, long timeoutMinutes) {
        redisTemplate.opsForValue().set("OTP::" + email, otp, timeoutMinutes, TimeUnit.MINUTES);
    }

    public String getOtp(String email) {
        return redisTemplate.opsForValue().get("OTP::" + email);
    }

    public void deleteOtp(String email) {
        redisTemplate.delete("OTP::" + email);
    }
}
