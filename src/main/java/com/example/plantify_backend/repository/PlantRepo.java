package com.example.plantify_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.plantify_backend.models.Plants;

public interface PlantRepo extends JpaRepository<Plants, Integer> {
    
}
