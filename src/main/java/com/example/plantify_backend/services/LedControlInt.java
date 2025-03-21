package com.example.plantify_backend.services;

import java.util.List;

import com.example.plantify_backend.dtos.LedControlDto;
import com.example.plantify_backend.models.LedControl;


public interface LedControlInt {
    List<LedControlDto> getAllLedControl();
    LedControlDto convert(LedControl ledControl);
}
