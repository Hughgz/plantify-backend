package com.example.plantify_backend.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.dtos.PlantDto;
import com.example.plantify_backend.models.Plants;
import com.example.plantify_backend.repository.PlantRepo;
import com.example.plantify_backend.services.PlantInt;


@Service
public class PlantService implements PlantInt {
    private final PlantRepo repository;
    public PlantService(PlantRepo repository) {
        this.repository = repository;
    }
    @Override
    public void addPlant(PlantDto plantDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPlant'");
    }

    @Override
    public List<PlantDto> getPlants() {
        List<Plants> plants = repository.findAll();
        return plants.stream().map(plant -> convert(plant)).toList();
    }
    @Override
    public PlantDto convert(Plants plants) {
        return new ModelMapper().map(plants, PlantDto.class);
    }
    
}
