package com.PulsePoint.PulsePoint.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PulsePoint.PulsePoint.service.AIService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService;
    }


    @GetMapping("/")
    @PreAuthorize("hasAnyRole('STAFF', 'PATIENT')")
    public ResponseEntity<String> getChatResponse(@RequestBody String message) {
        String response = aiService.generateResponse(message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
