package com.example.plantify_backend.services;

import com.example.plantify_backend.dtos.WifiSettingDto;
import com.example.plantify_backend.models.WifiSetting;

public interface WifiSettingInt {
    void createWifiSettings(String ssid, String password);
    WifiSettingDto getWifiSetting();
    void updateWifi(String ssid, String password);
    WifiSettingDto convert(WifiSetting wifiSetting);
}
