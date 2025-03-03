package com.example.plantify_backend.dtos;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class PlantDto {
    private int plantId;
    private String name;
    private String species;
    private int numOfPlants;
    private int age;
    private LocalDateTime createdAt = LocalDateTime.now();
}
