package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    @Autowired
    private AiService aiService;
    @PostMapping("/chat")
    public ResponseEntity<String>chat(@RequestParam String message)
    {
        try
        {
            String aiResponse = aiService.askAi(message);
            return ResponseEntity.ok(aiResponse);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:"+e.getMessage());
        }
    }
}
