package com.example.plantify_backend.services.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.services.BlackListTokenInt;

@Service
public class TokenBlackListService implements BlackListTokenInt{
    private final StringRedisTemplate stringRedisTemplate;

    public TokenBlackListService(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void blackListToken(String token, long expirationMinutes) {
        stringRedisTemplate.opsForValue().set("blacklist_token:" + token, "true", expirationMinutes, TimeUnit.MINUTES);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return stringRedisTemplate.hasKey("blacklist_token:" + token);
    }
    
}
