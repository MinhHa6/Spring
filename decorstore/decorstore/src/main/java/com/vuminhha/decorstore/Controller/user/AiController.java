package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.dto.ChatRequest;
import com.vuminhha.decorstore.dto.ChatResponse;
import com.vuminhha.decorstore.service.AiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class AiController {
    @Autowired
    private AiService aiService;




    @PostMapping("/ai-chat")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest request) {
        try {
            // Validate input
            String userMessage = request.getMessage();
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ChatResponse("Tin nhắn không được để trống"));
            }

            // Giới hạn độ dài tin nhắn
            if (userMessage.length() > 500) {
                return ResponseEntity.badRequest()
                        .body(new ChatResponse("Tin nhắn quá dài (tối đa 500 ký tự)"));
            }

            // Call AI service
            String aiResponse = aiService.generateContent(userMessage);

            return ResponseEntity.ok(new ChatResponse(aiResponse));

        } catch (Exception e) {
            // Log error properly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("Đã có lỗi xảy ra. Vui lòng thử lại sau."));
        }
    }
}
