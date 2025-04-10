package com.example.plantify_backend.services;


import com.example.plantify_backend.dtos.UserDto;
import com.example.plantify_backend.models.Users;

public interface UserInt {
    UserDto register(UserDto dto) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
    UserDto searchByPhone(String phone);
    UserDto convert(Users user);
}
