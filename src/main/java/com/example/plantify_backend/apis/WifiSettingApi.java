package com.example.plantify_backend.apis;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantify_backend.dtos.WifiSettingDto;
import com.example.plantify_backend.services.impl.WifiSettingService;

@RestController
@RequestMapping("/api/wifiSetting")
@CrossOrigin(origins = "*")
public class WifiSettingApi {

    private final WifiSettingService service;
    
    public WifiSettingApi(WifiSettingService wifiSettingService){
        this.service = wifiSettingService;
    }

    @GetMapping
    public ResponseEntity<?> getWifi(){
        WifiSettingDto wifiSettingDto = service.getWifiSetting();
        return ResponseEntity.ok(wifiSettingDto);
    }

    @PostMapping("/initial")
    public ResponseEntity<?> postWifi(@RequestBody WifiSettingDto wifiSettingDto){
        service.createWifiSettings(wifiSettingDto.getSsid(), wifiSettingDto.getPassword());
        return ResponseEntity.ok("Tao thanh cong");
    }

    @PutMapping("/update")
    public ResponseEntity<?> putWifi(@RequestParam String ssid, @RequestParam String password){
        service.updateWifi(ssid, password);
        return ResponseEntity.ok("Cap nhat thanh cong");
    }
}
