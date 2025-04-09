package com.example.plantify_backend.services;

public interface BlackListTokenInt {
    void blackListToken(String token, long expirationMinutes);
    boolean isTokenBlacklisted(String token);
}
