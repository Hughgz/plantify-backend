package com.example.plantify_backend.services;

import com.example.plantify_backend.dtos.SensorReadingDto;
import com.example.plantify_backend.models.SensorsReading;

import java.util.List;

public interface SensorReadingInt {
    List<SensorReadingDto> getAllSensorReading();
    void insertSensorReading(SensorReadingDto dto);
    SensorReadingDto convert(SensorsReading sensorsReading);
}
