package com.example.plantify_backend.repository;


import com.example.plantify_backend.models.SensorsReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorReadingRepo extends JpaRepository<SensorsReading, Integer> {
    @Query("SELECT s FROM SensorsReading s ORDER BY s.sensorReadingId DESC LIMIT 1")
    SensorsReading findLatestSensorReading();
    
    @Query("SELECT s FROM SensorsReading s WHERE CAST(s.regDate AS LocalDate) = :date ORDER BY s.regDate")
    List<SensorsReading> findSensorReadingsByDate(@Param("date") LocalDate date);
}
