package com.example.plantify_backend.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantify_backend.dtos.LedStatusDto;
import com.example.plantify_backend.repository.LedStatusRepo;
import com.example.plantify_backend.services.impl.LedStatusService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/ledStatus")
@CrossOrigin(origins = "*")
public class LedStatusApi {

    private final LedStatusService ledStatusService;

    public LedStatusApi(LedStatusService ledStatusService, LedStatusRepo ledStatusRepo){
        this.ledStatusService = ledStatusService;
    }

    @GetMapping
    public ResponseEntity<?> getLedStatus() {
        LedStatusDto ledStatusDto = ledStatusService.findById();
        if(ledStatusDto != null){
            return ResponseEntity.ok(ledStatusDto);
        }
        return ResponseEntity.badRequest().body("Khong tim thay led");
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateLed(
            @RequestParam String led, 
            @RequestParam Boolean status) {
        ledStatusService.updateLedStatus(led, status);
        return ResponseEntity.ok(Map.of("message", "Led updated"));
    }
}
