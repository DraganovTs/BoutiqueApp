package com.homecode.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "mails")
    public void listenerMessages(String message){
        var msgParts = message.split(":");
        log.info("Will perform a {} with content {}",msgParts[0],msgParts[1]);
    }
}
