package com.example.plantify_backend.dtos;

import lombok.Data;

@Data
public class WifiSettingDto {
    private int id;
    private String ssid;
    private String password;

    public WifiSettingDto(String ssid, String password){
        this.ssid = ssid;
        this.password = password;
    }
}
