package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.service.AiService;
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
    @ResponseBody
    public Map<String, String> sendMessage(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String aiResponse = aiService.generateContent(userMessage);

        return Map.of("response", aiResponse);
    }
}
