package com.example.plantify_backend.services.impl;
import com.example.plantify_backend.utils.JWTTokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.plantify_backend.dtos.LedStatusDto;
import com.example.plantify_backend.models.LedStatus;
import com.example.plantify_backend.repository.LedStatusRepo;
import com.example.plantify_backend.services.LedStatusInt;

@Service
public class LedStatusService implements LedStatusInt{

    private final JWTTokenUtils JWTTokenUtils;
    private final LedStatusRepo ledStatusRepo;

    public LedStatusService(LedStatusRepo ledStatusRepo, JWTTokenUtils JWTTokenUtils) {
        this.ledStatusRepo = ledStatusRepo;
        this.JWTTokenUtils = JWTTokenUtils;
    }

    @Override
    public LedStatusDto findById() {
        LedStatus ledStatus = ledStatusRepo.findById(1).orElseThrow(() -> new RuntimeException("Khong tim thay id"));
        return convert(ledStatus);
    }

    @Override
    public LedStatusDto convert(LedStatus ledStatus) {
        return new ModelMapper().map(ledStatus, LedStatusDto.class);
    }
    
    @Override
    public void updateLedStatus(String led, boolean status) {
    // Tìm bản ghi LedStatus với id = 1, nếu không tìm thấy thì ném ngoại lệ
        LedStatus ledStatus = ledStatusRepo.findById(1)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi với id = 1"));

    // Cập nhật trạng thái LED tương ứng
    switch (led) {
        case "led1":
            ledStatus.setLed1(status);
            break;
        case "led2":
            ledStatus.setLed2(status);
            break;
        case "led3":
            ledStatus.setLed3(status);
            break;
        case "led4":
            ledStatus.setLed4(status);
            break;
        default:
            throw new IllegalArgumentException("Tên LED không hợp lệ: " + led);
    }

    // Lưu thay đổi vào database
    ledStatusRepo.save(ledStatus);
}


}
