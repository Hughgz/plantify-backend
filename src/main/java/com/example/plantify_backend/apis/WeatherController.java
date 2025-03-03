package com.example.plantify_backend.apis;

import org.springframework.web.bind.annotation.*;
import com.example.plantify_backend.services.WeatherService;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*") // Cho phép frontend truy cập API
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // API lấy thời tiết theo tên thành phố
    @GetMapping("/{city}")
    public Map<String, Object> getWeather(@PathVariable String city) {
        System.out.println("Nhận request lấy thời tiết cho thành phố: " + city);
        Map<String, Object> response = weatherService.getWeatherByCity(city);
        System.out.println("Dữ liệu trả về: " + response);
        return response;
    }

    // API lấy thời tiết theo tọa độ (lat/lon)
    @GetMapping("/location")
    public Map<String, Object> getWeatherByLocation(@RequestParam double lat, @RequestParam double lon) {
        System.out.println("Nhận request lấy thời tiết cho tọa độ: Lat=" + lat + ", Lon=" + lon);
        Map<String, Object> response = weatherService.getWeatherByLocation(lat, lon);
        System.out.println("Dữ liệu trả về: " + response);
        return response;
    }
}
