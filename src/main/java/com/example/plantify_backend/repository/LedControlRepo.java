package com.example.plantify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plantify_backend.models.LedControl;

public interface LedControlRepo extends JpaRepository<LedControl, Integer>{
    
}
