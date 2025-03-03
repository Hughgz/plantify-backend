package com.example.plantify_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.plantify_backend.models.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByPhoneNumber(String phoneNumber);

    @Query("SELECT user FROM Users user WHERE user.fullName LIKE %:fullName%")
    List<Users> findByName(@Param("fullName") String fullName);

    @Query("SELECT user FROM Users user WHERE user.email LIKE %:email%")
    List<Users> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.phoneNumber = :phoneNumber")
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
