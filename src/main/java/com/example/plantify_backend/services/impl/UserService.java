package com.example.plantify_backend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.plantify_backend.dtos.UserDto;
import com.example.plantify_backend.exceptions.DataNotFoundException;
import com.example.plantify_backend.models.Role;
import com.example.plantify_backend.models.Users;
import com.example.plantify_backend.repository.RoleRepo;
import com.example.plantify_backend.repository.UserRepo;
import com.example.plantify_backend.response.LoginResponse;
import com.example.plantify_backend.services.UserInt;
import com.example.plantify_backend.utils.JWTTokenUtils;

@Service
public class UserService implements UserInt {
    private final UserRepo repository;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenUtils jwtTokenUtil;
    private final RedisTokenService redisTokenService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final UserDetailsService userDetailsService;
    private final TokenBlackListService tokenBlackListService;


    public UserService(UserRepo repository, RoleRepo roleRepo, PasswordEncoder passwordEncoder,
                       JWTTokenUtils jwtTokenUtil, AuthenticationManager authenticationManager,
                       RedisTokenService redisTokenService, UserDetailsService userDetailsService, TokenBlackListService tokenBlackListService) {
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.redisTokenService = redisTokenService;
        this.userDetailsService = userDetailsService;
        this.tokenBlackListService = tokenBlackListService;
        this.modelMapper = new ModelMapper();
    }

    @Override
    @Transactional
    public UserDto register(UserDto dto) throws Exception {
        String phoneNumber = dto.getPhoneNumber();

        // Kiểm tra xem số điện thoại đã tồn tại chưa
        if (repository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Tìm vai trò người dùng
        Role role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        // Chuyển đổi DTO sang entity Users
        Users newUser = modelMapper.map(dto, Users.class);
        newUser.setRole(role);
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Lưu vào database
        Users savedUser = repository.save(newUser);

        // Trả về DTO
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public LoginResponse login(String phoneNumber, String password) throws Exception {
        Users existingUser = repository.findByPhoneNumber(phoneNumber);
        if (existingUser == null) {
            throw new DataNotFoundException("Phone does not exist");
        }

        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Password doesn't match, please try again");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        String accessToken = jwtTokenUtil.generateToken(existingUser);
        String refreshToken = jwtTokenUtil.generateRefreshToken(existingUser); // Tạo refresh token

        // Lưu refresh token vào Redis
        redisTokenService.saveRefeshToken(
            existingUser.getUserId(), // key
            refreshToken,
            7 * 24 * 60 // TTL 7 ngày
        );
        System.out.println(">> Saving refresh token for userId = " + existingUser.getUserId());
        return LoginResponse.builder()
                .message("Login successful")
                .token(accessToken)
                .refreshToken(refreshToken)
                .user(convert(existingUser))
                .build();
    }
    @Override
    public ResponseEntity<?> refreshAccessToken(String refreshToken) {
        try {
            String phone = jwtTokenUtil.extractPhone(refreshToken);
            Users user = (Users) userDetailsService.loadUserByUsername(phone);

            String savedToken = redisTokenService.getRefreshToken(user.getUserId());

            if (savedToken == null || !savedToken.equals(refreshToken)) {
                return ResponseEntity.status(401).body("Refresh token is invalid or expired");
            }

            String newAccessToken = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok(java.util.Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token: " + e.getMessage());
        }
    }
    @Override
    public UserDto searchByPhone(String phone) {
        Users user = repository.findByPhoneNumber(phone);
        return convert(user);
    }

    @Override
    public ResponseEntity<?> logout(String refreshToken, String accessToken) {
        try {
            String phone = jwtTokenUtil.extractPhone(refreshToken);
            Users user = (Users) userDetailsService.loadUserByUsername(phone);

            String savedToken = redisTokenService.getRefreshToken(user.getUserId());
            if (savedToken == null || !savedToken.equals(refreshToken)) {
                return ResponseEntity.status(401).body("Invalid refresh token");
            }

            redisTokenService.deleteRefeshToken(user.getUserId());

            // ✅ Đưa accessToken vào blacklist
            long tokenTTL = jwtTokenUtil.getTokenRemainingTime(accessToken);
            tokenBlackListService.blackListToken(accessToken, tokenTTL);

            return ResponseEntity.ok("Logged out and access token blacklisted");

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token: " + e.getMessage());
        }
    }


    @Override
    public UserDto convert(Users user) {
        return modelMapper.map(user, UserDto.class);
    }
}
