package com.example.plantify_backend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SensorReadingDto {
    private int sensorReadingId;
    private int led;
    private float lux;
    private float nitrogen;
    private float phosphorus;
    private float potassium;
    private float soilConductivity;
    private float soilHumidity;
    private float soilPH;
    private float waterMeter;
    private float soilTemperature;
    private float temperature;
    private float humidity;
    private int weather;
}
