package com.example.plantify_backend.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantify_backend.services.impl.ChatboxService;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbox")
// Cho phép tất cả origin khi test, sau này giới hạn lại nếu cần
@CrossOrigin(origins = {"http://localhost:3000", "*"}) 
public class ChatboxApi {

    private final ChatboxService chatService;

    public ChatboxApi(ChatboxService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        try {
            String userMessage = request.get("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
            }
            String reply = chatService.getChatbotResponse(userMessage);
            return ResponseEntity.ok(Map.of("reply", reply));
        } catch (Exception e) {
            // Log lỗi nếu cần (dùng logger như SLF4J trong thực tế)
            System.err.println("Error in ChatboxApi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }
}

