package com.example.plantify_backend.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantify_backend.dtos.LedControlDto;
import com.example.plantify_backend.services.impl.LedControlService;

@RestController
@RequestMapping("/api/ledControl")
@CrossOrigin(origins = "*")
public class LedControlApi {

    private final LedControlService service;

    public LedControlApi(LedControlService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<LedControlDto> listControlDtos = service.getAllLedControl();
        return ResponseEntity.ok(listControlDtos);
    }
}
