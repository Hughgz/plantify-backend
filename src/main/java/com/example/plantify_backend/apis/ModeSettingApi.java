package com.example.plantify_backend.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantify_backend.dtos.ModeSettingDto;
import com.example.plantify_backend.services.impl.ModeSettingService;

@RestController
@RequestMapping("/api/modeSetting")
@CrossOrigin(origins = "*")

public class ModeSettingApi {

    private final ModeSettingService modeSettingService;

    public ModeSettingApi(ModeSettingService modeSettingService) {
        this.modeSettingService = modeSettingService;
    }

    @GetMapping
    public ResponseEntity<?> getModeSetting() {
        ModeSettingDto modeSettingDto = modeSettingService.findById();
        if(modeSettingDto != null){
            return ResponseEntity.ok(modeSettingDto);
        }
        return ResponseEntity.badRequest().body("Khong tim thay mod");
    }

    @PutMapping("/updateMode")
    public ResponseEntity<?> updateModeSetting(@RequestParam int mode) {
        modeSettingService.updateModeSetting(mode);
        return ResponseEntity.ok("Mode updated");
    }
}
