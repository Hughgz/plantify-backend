package com.example.plantify_backend.dtos;

import java.time.LocalTime;

import lombok.Data;

@Data
public class LedControlDto {
    private int id;
    private String ledName;
    private LocalTime turnOnTime;
    private LocalTime turnOffTime;
}
