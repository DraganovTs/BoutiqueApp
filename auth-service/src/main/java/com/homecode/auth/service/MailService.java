package com.homecode.auth.service;

import com.homecode.auth.model.entity.UserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendWelcomeMail(UserCredential user) {
        this.kafkaTemplate.send("mails", "user.creation: " + user.getName() + ":" + user.getEmail());
    }
}
