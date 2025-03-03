package com.example.plantify_backend.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table
@Entity
@Data
public class Plants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plantId;
    private String name;
    private String species;
    private int numOfPlants;
    private int age;
    private LocalDateTime createdAt = LocalDateTime.now();
}
