package com.example.plantify_backend.apis;

import com.example.plantify_backend.dtos.SensorReadingDto;
import com.example.plantify_backend.services.impl.SensorReadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensorReading")
@CrossOrigin(origins = "*")
public class SensorReadingApi {

    private final SensorReadingService service;

    public SensorReadingApi(SensorReadingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllSensorReading(){
        List<SensorReadingDto>  sensorReadingDtos = service.getAllSensorReading();
        if(sensorReadingDtos.isEmpty()){
            return ResponseEntity.badRequest().body("Khong ton tai du lieu");
        }else{
            return ResponseEntity.ok(sensorReadingDtos);
        }
    }
}
