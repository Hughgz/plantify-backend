package com.example.plantify_backend.models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "led_control")
@Data
public class LedControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "led_name", length = 10, nullable = false)
    private String ledName;

    @Column(name = "turn_on_time", nullable = false)
    private LocalTime turnOnTime;

    @Column(name = "turn_off_time", nullable = false)
    private LocalTime turnOffTime;
}