package com.example.plantify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plantify_backend.models.LedStatus;

public interface LedStatusRepo extends JpaRepository<LedStatus, Integer> {
    
}
