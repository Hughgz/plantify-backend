package com.example.plantify_backend.models;


import jakarta.persistence.*;
import lombok.Data;

@Table
@Data
@Entity
public class RecommendSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendId;
    private String diseaseName;
    private String recommendedFertilizer; //phan bon
    private String recommendedPesticide; //thuoc sau
    private String solution;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plants plant;
}
