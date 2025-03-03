package com.example.plantify_backend.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty("user_id")
    private int userId;
    private String fullName;
    @Size(min = 10, max = 10, message = "Phone must 10 characters")
    private String phoneNumber;
    private String email;
    private String password;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @JsonProperty("role_name")
    private String roleName;
    @JsonProperty("role_id")
    private int roleId;
}
