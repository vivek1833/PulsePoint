package com.PulsePoint.PulsePoint.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.PulsePoint.PulsePoint.utils.Constants;

@Service
public class EmailEventConsumer {
    private final EmailService emailService;

    public EmailEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = Constants.EMAIL_TOPIC, groupId = "pulsepoint-group")
    public void consumeEmailEvent(String message) {

        String[] parts = message.split("\\|\\|", 3);
        if (parts.length == 3) {
            emailService.sendEmail(parts[0], parts[1], parts[2]);
        }
    }
}
