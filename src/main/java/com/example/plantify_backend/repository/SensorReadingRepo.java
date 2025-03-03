package com.example.plantify_backend.repository;


import com.example.plantify_backend.models.SensorsReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorReadingRepo extends JpaRepository<SensorsReading, Integer> {

}
