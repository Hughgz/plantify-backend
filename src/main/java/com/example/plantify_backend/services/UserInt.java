package com.example.plantify_backend.services;

import org.springframework.http.ResponseEntity;

import com.example.plantify_backend.dtos.UserDto;
import com.example.plantify_backend.models.Users;
import com.example.plantify_backend.response.LoginResponse;

public interface UserInt {
    UserDto register(UserDto dto) throws Exception;
    LoginResponse login(String phoneNumber, String password) throws Exception;
    UserDto searchByPhone(String phone);
    UserDto convert(Users user);
    ResponseEntity<?> refreshAccessToken(String refreshToken);
    ResponseEntity<?> logout(String refreshToken, String accessToken);
}
