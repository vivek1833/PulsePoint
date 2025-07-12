package com.PulsePoint.PulsePoint.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.PulsePoint.PulsePoint.utils.Constants;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmailService(JavaMailSender mailSender, KafkaTemplate<String, String> kafkaTemplate) {
        this.mailSender = mailSender;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEmailEvent(String to, String subject, String text) {
        String payload = to + "||" + subject + "||" + text;
        kafkaTemplate.send(Constants.EMAIL_TOPIC, payload);
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}