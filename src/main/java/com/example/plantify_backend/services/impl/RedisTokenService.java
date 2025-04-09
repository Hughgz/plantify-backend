package com.example.plantify_backend.services.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.services.RedisTokenInt;

@Service
public class RedisTokenService implements RedisTokenInt {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisTokenService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void saveRefeshToken(int userId, String token, long minutes) {
        stringRedisTemplate.opsForValue().set("refresh_token:" + userId, token, minutes, TimeUnit.MINUTES);
    }

    @Override
    public String getRefreshToken(int userId) {
        // ✅ Bỏ dấu cách sau dấu `:`
        return stringRedisTemplate.opsForValue().get("refresh_token:" + userId);
    }

    @Override
    public void deleteRefeshToken(int userId) {
        // ✅ Bỏ dấu cách sau dấu `:`
        stringRedisTemplate.delete("refresh_token:" + userId);
    }
}
