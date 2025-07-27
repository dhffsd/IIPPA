package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.config.RabbitMQConfig;
import com.xymzsfxy.backend.entity.UserMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePushService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessageToUser(Long userId, UserMessage message) {
        String routingKey = "user.message." + userId;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, message);
    }
} 