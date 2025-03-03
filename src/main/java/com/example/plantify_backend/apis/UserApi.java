package com.example.plantify_backend.apis;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.plantify_backend.dtos.UserDto;
import com.example.plantify_backend.dtos.UserLoginDto;
import com.example.plantify_backend.response.LoginResponse;
import com.example.plantify_backend.response.RegisterResponse;
import com.example.plantify_backend.services.impl.UserService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private final UserService service;

    public UserApi(UserService service ) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDto userDTO,
            BindingResult result
    ) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }
        try {
            UserDto user = service.register(userDTO);
            registerResponse.setMessage("Register successfully");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDto userLoginDTO) {
    try {
        // Validate phone number format
        if (!isValidPhoneNumber(userLoginDTO.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message("Số điện thoại không hợp lệ")
                            .build()
            );
        }

        // Attempt login with phone number and password
        String token = service.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        if (token == null) {
            // If the token is null, it means login failed (incorrect phone number or password)
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message("Số điện thoại hoặc mật khẩu không chính xác")
                            .build()
            );
        }

        // After login success, fetch user details
        UserDto userDTO = service.searchByPhone(userLoginDTO.getPhoneNumber());
        if (userDTO == null) {
            throw new RuntimeException("Không tìm thấy người dùng, vui lòng thử lại");
        }

        // Create successful login response
        return ResponseEntity.ok(
                LoginResponse.builder()
                        .message("Login successfully")
                        .token(token)
                        .user(userDTO)
                        .build()
        );
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(
                LoginResponse.builder()
                        .message("Tài khoản hoặc mật khẩu không đúng, vui lòng thử lại")
                        .build()
        );
    }
}
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(0[3|5|7|8|9][0-9]{8})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
