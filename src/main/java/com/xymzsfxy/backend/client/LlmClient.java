package com.xymzsfxy.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class LlmClient {

    // 百炼OpenAI兼容模式API地址
    @Value("${llm.endpoint}")
    private String endpoint;

    // 百炼API Key
    private static final String API_KEY = "sk-82941f93d8964713aea0742ce6fad8fd";

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateResponse(String prompt, List<?> context) {
        // 构造OpenAI兼容模式的请求体
        Map<String, Object> request = Map.of(
            "model", "qwen-plus", // 可根据实际模型名调整
            "messages", context
            // 如需额外参数，可加 "extra_body", Map.of("enable_thinking", false)
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // 百炼兼容OpenAI返回格式：{"choices":[{"message":{"content":"..."}}], ...}
                List choices = (List) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map choice = (Map) choices.get(0);
                    Map message = (Map) choice.get("message");
                    if (message != null && message.get("content") != null) {
                        String content = message.get("content").toString();
                        return content;
                    }
                }
            }
            return "系统繁忙，请稍后再试";
        } catch (Exception e) {
            e.printStackTrace(); // 打印详细异常日志，便于排查问题
            return "系统繁忙，请稍后再试";
        }
    }
} 