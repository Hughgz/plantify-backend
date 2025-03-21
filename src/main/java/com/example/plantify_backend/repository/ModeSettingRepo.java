package com.example.plantify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plantify_backend.models.ModeSetting;

public interface ModeSettingRepo extends JpaRepository<ModeSetting, Integer>{
    
}
