package com.xymzsfxy.backend.controller.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketTestController {
    @MessageMapping("/hello")
    @SendTo("/topic/message/test")
    public String greeting(String message) {
        return "Hello, " + message;
    }
} 