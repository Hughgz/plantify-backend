package com.example.plantify_backend.models;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class SensorsReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sensorReadingId;
    private int led;
    private float lux;
    private float nitrogen;
    private float phosphorus;
    private float potassium;
    private float soilConductivity;
    private float soilHumidity;
    private float soilPH;
    private float rain;
    private float soilTemperature;
    private float temperature;
    private float humidity;
    private int weather;
    private float flowSensor1;
    private float flowSensor2;
    private LocalDateTime regDate;
}
