package com.xymzsfxy.backend.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "user.message.exchange";
    public static final String QUEUE = "user.message.queue.2";
    public static final String ROUTING_KEY = "user.message.key.2";

    @Bean
    public DirectExchange userMessageExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue userMessageQueue2() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding userMessageBinding2(Queue userMessageQueue2, DirectExchange userMessageExchange) {
        return BindingBuilder.bind(userMessageQueue2).to(userMessageExchange).with(ROUTING_KEY);
    }
} 