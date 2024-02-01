package com.joelmaciel.messageservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

    @RabbitListener(queues = {"${orderservice.queue.name}"})
    public void receivedMessage(String message) {
        log.info("Message received {}", message);
    }
}
