package com.PulsePoint.PulsePoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.service.AIService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
@Primary
public class GeminiService implements AIService {

    private final Client geminiClient;

    public GeminiService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    @Override
    public String generateResponse(String prompt) {
        try {
            GenerateContentResponse response = geminiClient.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null);

            return response.text();
        } catch (Exception e) {
            return "Error generating response: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();       
    }
}
