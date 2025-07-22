package com.xymzsfxy.backend.controller;

import com.xymzsfxy.backend.entity.AssistantResponse;
import com.xymzsfxy.backend.service.orchestrator.OrchestratorService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/assistant")
public class AssistantController {

    @Autowired
    private OrchestratorService orchestrator;

    @PostMapping("/chat")
    public AssistantResponse chat(@RequestBody ChatRequest request) {
        return orchestrator.processMessage(request.getMessages(), request.getSessionId());
    }

    @Data
    public static class ChatRequest {
        private String sessionId;
        private List<Message> messages;
    }

    @Data
    public static class Message {
        private String role;    // user/system/assistant
        private String content;
        private Map<String, Object> metadata;
    }
} 