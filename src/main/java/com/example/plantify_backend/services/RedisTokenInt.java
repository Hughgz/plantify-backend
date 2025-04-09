package com.example.plantify_backend.services;

public interface RedisTokenInt {
    void saveRefeshToken(int userId, String token, long minutes);
    String getRefreshToken(int userId);
    void deleteRefeshToken(int userId);
}
