package com.xymzsfxy.backend.service.agent;

import com.xymzsfxy.backend.entity.AssistantResponse;
import com.xymzsfxy.backend.client.LlmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderAgentService {
    @Autowired
    private LlmClient llmClient;

    public AssistantResponse createDraftOrder(String userInput) {
        String topic = extractTopic(userInput);
        String aiReply = llmClient.generateResponse(topic, List.of(
            Map.of("role", "system", "content", "你是智链工采的AI助手，专注于采购、供应链、比价、推荐、流程提醒等企业服务。你可以用专业、友好的语气为用户生成采购单、确认采购信息。"),
            Map.of("role", "user", "content", String.valueOf(userInput))
        ));
        AssistantResponse response = new AssistantResponse();
        response.setType(AssistantResponse.ResponseType.FORM);
        response.setTitle("采购单确认");
        response.setContent(aiReply);
        return response;
    }

    private String extractTopic(String userInput) {
        String prompt = "请从以下用户输入中提取最有可能的主题或商品名，直接返回即可，不要多余内容。用户输入：" + userInput;
        String aiExtracted = llmClient.generateResponse(prompt, List.of(
            Map.of("role", "system", "content", "你是一个主题/商品名抽取助手。"),
            Map.of("role", "user", "content", prompt)
        ));
        if (aiExtracted != null && !aiExtracted.isBlank()) {
            System.out.println("大模型抽取主题/商品名: " + aiExtracted);
            return aiExtracted.trim();
        }
        // 兜底返回原文
        return userInput;
    }
} 