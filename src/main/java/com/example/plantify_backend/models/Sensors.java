package com.example.plantify_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Sensors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sensorId;
    private String name;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<IrrigationSystems> irrigationSystems;

}
