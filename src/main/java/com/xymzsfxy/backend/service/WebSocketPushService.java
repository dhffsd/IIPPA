package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.entity.UserMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketPushService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 示例：监听用户2的队列，实际应动态声明
    @RabbitListener(queues = "user.message.queue.2")
    public void receiveMessage(UserMessage message) {
        Long userId = message.getReceiverId();
        messagingTemplate.convertAndSend("/topic/message/" + userId, message);
    }
} 