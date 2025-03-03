package com.example.plantify_backend.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantify_backend.dtos.PlantDto;
import com.example.plantify_backend.services.impl.PlantService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/plants")
public class PlantApi {
    private final PlantService plantService;
    public PlantApi(PlantService plantService) {
        this.plantService = plantService;
    }

    
    @GetMapping
    public ResponseEntity<?> addPlant() {
        List<PlantDto> plants = plantService.getPlants();
        if(plants.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(plants);
    }
}
