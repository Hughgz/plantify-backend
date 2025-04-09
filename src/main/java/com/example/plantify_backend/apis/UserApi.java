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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")

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
            if (!isValidPhoneNumber(userLoginDTO.getPhoneNumber())) {
                return ResponseEntity.badRequest().body(
                        LoginResponse.builder()
                                .message("Số điện thoại không hợp lệ")
                                .build()
                );
            }

            LoginResponse loginResponse = service.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword()
            );

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message("Tài khoản hoặc mật khẩu không đúng, vui lòng thử lại")
                            .build()
            );
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }
        return service.refreshAccessToken(refreshToken);
    }  
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }

        String accessToken = authHeader.replace("Bearer ", "");
        return service.logout(refreshToken, accessToken);
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(0[3|5|7|8|9][0-9]{8})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
