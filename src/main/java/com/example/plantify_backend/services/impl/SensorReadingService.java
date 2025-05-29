package com.example.plantify_backend.services.impl;


import com.example.plantify_backend.dtos.SensorReadingDto;
import com.example.plantify_backend.models.SensorsReading;
import com.example.plantify_backend.repository.SensorReadingRepo;
import com.example.plantify_backend.services.SensorReadingInt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorReadingService implements SensorReadingInt {
    private final SensorReadingRepo repository;

    public SensorReadingService(SensorReadingRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<SensorReadingDto> getAllSensorReading() {
        List<SensorsReading> sensorsReadings = repository.findAll();
        return sensorsReadings.stream().map(sensor -> convert(sensor)).collect(Collectors.toList());
    }

    @Override
    public SensorReadingDto convert(SensorsReading sensorsReading) {
        return new ModelMapper().map(sensorsReading, SensorReadingDto.class);
    }

    @Override
    public void insertSensorReading(SensorReadingDto dto) {
        SensorsReading sensorsReading = new ModelMapper().map(dto, SensorsReading.class);
        
        // Set current date and time if regDate is null
        if (sensorsReading.getRegDate() == null) {
            sensorsReading.setRegDate(LocalDateTime.now());
        }
        
        repository.save(sensorsReading);
    }
    
    @Override
    public SensorReadingDto getLatestSensorReading() {
        SensorsReading latestReading = repository.findLatestSensorReading();
        if (latestReading == null) {
            return null;
        }
        return convert(latestReading);
    }
    
    @Override
    public List<SensorReadingDto> getSensorReadingsByDate(LocalDate date) {
        List<SensorsReading> readings = repository.findSensorReadingsByDate(date);
        return readings.stream().map(this::convert).collect(Collectors.toList());
    }
}
