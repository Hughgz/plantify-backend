package com.example.plantify_backend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.dtos.WifiSettingDto;
import com.example.plantify_backend.models.WifiSetting;
import com.example.plantify_backend.repository.WifiSettingRepo;
import com.example.plantify_backend.services.WifiSettingInt;
import com.example.plantify_backend.utils.EncryptionUtil;

@Service
public class WifiSettingService implements WifiSettingInt{

    private final WifiSettingRepo wifiSettingRepo;
    private final EncryptionUtil encryptionUtil;

    public WifiSettingService(WifiSettingRepo wifiSettingRepo, EncryptionUtil encryptionUtil) {
        this.wifiSettingRepo = wifiSettingRepo;
        this.encryptionUtil =encryptionUtil;
    }
    @Override
    public void createWifiSettings(String ssid, String password) {
        String encryptedPassword = encryptionUtil.encrypt(password);
        WifiSetting wifiSettings = new WifiSetting();
        wifiSettings.setSsid(ssid);
        wifiSettings.setPassword(encryptedPassword);
        wifiSettingRepo.save(wifiSettings);
    }
    @Override
    public WifiSettingDto getWifiSetting() {
        WifiSetting wifiSettings = wifiSettingRepo.findById(1)
            .orElseThrow(() -> new RuntimeException("WiFi settings not found"));
        String decryptedPassword = encryptionUtil.decrypt(wifiSettings.getPassword());
        return new WifiSettingDto(wifiSettings.getSsid(), decryptedPassword);
    }

    @Override
    public void updateWifi(String ssid, String password) {
        WifiSetting wifiSetting = wifiSettingRepo.findById(1).orElseThrow(() -> new RuntimeException("Khong tim thay wifi"));
        wifiSetting.setSsid(ssid);
        String encryptedPassword = encryptionUtil.encrypt(password);
        wifiSetting.setPassword(encryptedPassword);
        wifiSettingRepo.save(wifiSetting);
    }

    @Override
    public WifiSettingDto convert(WifiSetting wifiSetting) {
        return new ModelMapper().map(wifiSetting, WifiSettingDto.class);
    }
    
}
