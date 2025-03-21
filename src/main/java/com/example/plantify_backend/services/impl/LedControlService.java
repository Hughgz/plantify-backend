package com.example.plantify_backend.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.dtos.LedControlDto;
import com.example.plantify_backend.dtos.LedStatusDto;
import com.example.plantify_backend.models.LedControl;
import com.example.plantify_backend.repository.LedControlRepo;
import com.example.plantify_backend.services.LedControlInt;

@Service
public class LedControlService implements LedControlInt{
    private final LedControlRepo ledControlRepo;

    public LedControlService(LedControlRepo ledControlRepo) {
        this.ledControlRepo = ledControlRepo;
    }

    @Override
    public List<LedControlDto> getAllLedControl() {
        List<LedControl> ledControls = ledControlRepo.findAll();
        return ledControls.stream().map(ledControl -> convert(ledControl)).toList();
    }

    @Override
    public LedControlDto convert(LedControl ledControl) {
        return new ModelMapper().map(ledControl, LedControlDto.class);
    }


}
