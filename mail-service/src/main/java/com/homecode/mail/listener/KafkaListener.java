package com.homecode.mail.listener;

import com.homecode.mail.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class KafkaListener {

    private final MailService mailService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "mails")
    public void listenerMessages(String message) {
        var msgParts = message.split(":");
        log.info("Will perform a {} with content {} to email: {}", msgParts[0], msgParts[1],msgParts[2]);
        this.mailService.sendEmail(msgParts[2], "welcomeMail", msgParts[1]);
    }
}
