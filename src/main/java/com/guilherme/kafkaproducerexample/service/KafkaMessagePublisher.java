package com.guilherme.kafkaproducerexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, Object> template;

    @Autowired
    public KafkaMessagePublisher(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("quickstart-3", message);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Unable to send message[" + message + "] due to: " + ex.getMessage());
            } else {
                System.out.println("Sent message[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
        });
    }

    }
