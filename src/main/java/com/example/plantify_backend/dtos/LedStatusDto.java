package com.example.plantify_backend.dtos;

import lombok.Data;

@Data
public class LedStatusDto {
    private Integer id;
    private Byte led1;
    private Byte led2;
    private Byte led3;
    private Byte led4;
}
