package com.example.plantify_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final RestTemplate restTemplate = new RestTemplate();

    // Lấy thời tiết theo thành phố
    public Map<String, Object> getWeatherByCity(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, city, API_KEY);
        return fetchWeatherData(url);
    }

    // Lấy thời tiết theo tọa độ (lat/lon)
    public Map<String, Object> getWeatherByLocation(double lat, double lon) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric", BASE_URL, lat, lon, API_KEY);
        return fetchWeatherData(url);
    }

    // Gọi OpenWeather API và xử lý lỗi
    private Map<String, Object> fetchWeatherData(String url) {
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("main")) {
                return createErrorResponse("Không tìm thấy dữ liệu thời tiết.");
            }
            return response;
        } catch (HttpClientErrorException e) {
            return createErrorResponse("Lỗi khi gọi OpenWeather API: " + e.getMessage());
        } catch (Exception e) {
            return createErrorResponse("Lỗi hệ thống: " + e.getMessage());
        }
    }

    // Tạo phản hồi lỗi
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}
