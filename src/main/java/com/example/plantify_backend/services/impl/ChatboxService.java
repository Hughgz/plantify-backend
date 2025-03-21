package com.example.plantify_backend.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatboxService {

    @Value("${api_key}")
    private String openAiApiKey;

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getChatbotResponse(String userMessage) {
        // Kiểm tra API Key
        if (openAiApiKey == null || openAiApiKey.trim().isEmpty()) {
            return "Lỗi: API Key không được cấu hình";
        }

        try {
            // Tạo header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + openAiApiKey);

            // Tạo body yêu cầu
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "Bạn là trợ lý ảo thông minh"),
                Map.of("role", "user", "content", userMessage)
            ));

            // Gửi yêu cầu
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            Map<String, Object> response = restTemplate.postForObject(OPENAI_API_URL, entity, Map.class);

            // Xử lý phản hồi
            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
            return "Lỗi phản hồi từ AI: Không có dữ liệu trả về";
        } catch (HttpClientErrorException e) {
            return "Lỗi từ OpenAI: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Lỗi khi gọi API OpenAI: " + e.getMessage();
        }
    }
}