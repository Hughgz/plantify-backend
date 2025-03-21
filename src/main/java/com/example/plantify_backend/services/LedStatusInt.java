package com.example.plantify_backend.services;

import com.example.plantify_backend.dtos.LedStatusDto;
import com.example.plantify_backend.models.LedStatus;

public interface LedStatusInt {
    LedStatusDto findById();
    void updateLedStatus(String led, boolean status);
    LedStatusDto convert(LedStatus ledStatus);
}
