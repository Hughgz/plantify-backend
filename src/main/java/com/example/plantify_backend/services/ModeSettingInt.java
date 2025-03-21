package com.example.plantify_backend.services;

import com.example.plantify_backend.dtos.ModeSettingDto;
import com.example.plantify_backend.models.ModeSetting;

public interface ModeSettingInt {
    ModeSettingDto findById();
    void updateModeSetting(int mode);
    ModeSettingDto convert(ModeSetting modeSetting);
}
