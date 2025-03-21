package com.example.plantify_backend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.dtos.LedStatusDto;
import com.example.plantify_backend.dtos.ModeSettingDto;
import com.example.plantify_backend.models.LedStatus;
import com.example.plantify_backend.models.ModeSetting;
import com.example.plantify_backend.repository.ModeSettingRepo;
import com.example.plantify_backend.services.ModeSettingInt;


@Service
public class ModeSettingService implements ModeSettingInt{

    private final ModeSettingRepo modeSettingRepo;

    public ModeSettingService(ModeSettingRepo modeSettingRepo) {
        this.modeSettingRepo = modeSettingRepo;
    }

    @Override
    public ModeSettingDto findById() {
        ModeSetting modeSetting = modeSettingRepo.findById(1).orElseThrow(() -> new RuntimeException("Khong tim thay mode"));
        return convert(modeSetting);
    }

    @Override
    public ModeSettingDto convert(ModeSetting modeSetting) {
        return new ModelMapper().map(modeSetting, ModeSettingDto.class);
    }

    @Override
    public void updateModeSetting(int mode) {
        ModeSetting modeSetting = modeSettingRepo.findById(1).orElseThrow(() -> new RuntimeException("Khong tim thay id"));
        modeSetting.setMode(mode);
        modeSettingRepo.save(modeSetting);
    }
    
}
