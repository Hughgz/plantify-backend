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
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;


    public UserService(UserRepo repository, RoleRepo roleRepo, PasswordEncoder passwordEncoder,
                       JWTTokenUtils jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
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
    public String login(String phoneNumber, String password) throws Exception {
        Users existingUser = repository.findByPhoneNumber(phoneNumber);
        if (existingUser == null) {
            throw new DataNotFoundException("Phone does not exist");
        }

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Password doesn't match, please try again");
        }

        // Xác thực bằng Spring Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password, existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);

        // Tạo JWT token
        return jwtTokenUtil.generateToken(existingUser);
    }
    @Override
    public UserDto searchByPhone(String phone) {
        Users user = repository.findByPhoneNumber(phone);
        return convert(user);
    }



    @Override
    public UserDto convert(Users user) {
        return modelMapper.map(user, UserDto.class);
    }
}
