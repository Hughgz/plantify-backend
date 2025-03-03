package com.example.plantify_backend.services;

import java.util.List;

import com.example.plantify_backend.dtos.PlantDto;
import com.example.plantify_backend.models.Plants;

public interface PlantInt {
    void addPlant(PlantDto plantDto);
    List<PlantDto> getPlants();
    PlantDto convert(Plants plants);
}
