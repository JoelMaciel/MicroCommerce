package com.joelmaciel.orderservice.api.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Value("${orderservice.queue.name}")
    private String name;

    @Bean
    public Queue queue() {
        return new Queue(name, true);
    }

}
