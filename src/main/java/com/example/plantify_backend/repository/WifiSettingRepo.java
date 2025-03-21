package com.example.plantify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plantify_backend.models.WifiSetting;

public interface WifiSettingRepo extends JpaRepository<WifiSetting, Integer> {
    
}
