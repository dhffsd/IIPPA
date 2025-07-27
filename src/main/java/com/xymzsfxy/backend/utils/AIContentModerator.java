// src/main/java/com/xymzsfxy/backend/utils/AIContentModerator.java
package com.xymzsfxy.backend.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xymzsfxy.backend.client.LlmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * AI内容审核工具类
 * 专门负责AI审核逻辑，其他服务通过调用此类进行内容审核
 */
@Component
public class AIContentModerator {

    @Autowired
    private LlmClient llmClient;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 审核内容
     * @param contentType 内容类型 (article/comment)
     * @param title 标题
     * @param content 内容
     * @param category 分类
     * @return 审核结果
     */
    public ModerationResult moderateContent(String contentType, String title, String content, String category) {
        try {
            // 1. 内容预处理
            String processedContent = preprocessContent(content);

            // 2. AI审核
            return performAIModeration(contentType, title, processedContent, category);

        } catch (Exception e) {
            // 审核失败时的降级处理
            return createFallbackResult(contentType, e.getMessage());
        }
    }

    /**
     * 批量审核内容
     * @param contents 内容列表
     * @return 审核结果列表
     */
    public List<ModerationResult> batchModerate(List<ContentItem> contents) {
        List<ModerationResult> results = new ArrayList<>();

        for (ContentItem item : contents) {
            ModerationResult result = moderateContent(
                    item.getContentType(),
                    item.getTitle(),
                    item.getContent(),
                    item.getCategory()
            );
            result.setContentId(item.getContentId());
            results.add(result);
        }

        return results;
    }

    private String preprocessContent(String content) {
        if (content == null) return "";

        // 1. 清理HTML标签
        content = content.replaceAll("<[^>]*>", "");

        // 2. 去除多余空白字符
        content = content.replaceAll("\\s+", " ").trim();

        // 3. 敏感词预处理（可选）
        // content = sensitiveWordFilter.filter(content);

        return content;
    }

    private ModerationResult performAIModeration(String contentType, String title, String content, String category) {
        // 构建AI审核提示词
        String prompt = buildModerationPrompt(contentType, title, content, category);

        // 调用AI进行审核
        String aiResponse = llmClient.generateResponse(prompt, Arrays.asList(
                Map.of("role", "system", "content", "你是一个专业的内容审核AI助手，负责审核用户发布的内容是否符合平台规范。请严格按照JSON格式返回结果。"),
                Map.of("role", "user", "content", prompt)
        ));

        // 解析AI响应
        return parseAIResponse(aiResponse, contentType);
    }

    private String buildModerationPrompt(String contentType, String title, String content, String category) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是一个专业的内容审核AI助手。请严格按照以下JSON格式返回审核结果，不要包含任何其他文字：\n\n");

        prompt.append("内容类型：").append(contentType).append("\n");
        prompt.append("内容分类：").append(category).append("\n");
        prompt.append("内容标题：").append(title).append("\n");
        prompt.append("内容正文：").append(content).append("\n\n");

        prompt.append("审核标准：\n");
        prompt.append("1. 政治敏感：不得包含政治敏感内容\n");
        prompt.append("2. 暴力血腥：不得包含暴力、血腥内容\n");
        prompt.append("3. 色情低俗：不得包含色情、低俗内容\n");
        prompt.append("4. 商业广告：不得包含未经授权的商业广告\n");
        prompt.append("5. 个人信息：不得泄露他人个人信息\n");
        prompt.append("6. 版权侵权：不得侵犯他人版权\n");
        prompt.append("7. 垃圾信息：不得包含垃圾信息、恶意链接\n\n");

        prompt.append("请严格按照以下JSON格式返回，不要添加任何其他内容：\n");
        prompt.append("{\n");
        prompt.append("  \"decision\": \"PASS\",\n");
        prompt.append("  \"confidence\": 0.95,\n");
        prompt.append("  \"violations\": [],\n");
        prompt.append("  \"reason\": \"内容符合规范\"\n");
        prompt.append("}\n\n");

        prompt.append("如果内容违规，请将decision改为\"REJECT\"，并在violations数组中添加违规类型，在reason中说明原因。\n");
        prompt.append("如果无法确定，请将decision改为\"REVIEW\"。\n");

        return prompt.toString();
    }

    private ModerationResult parseAIResponse(String aiResponse, String contentType) {
        try {
            // 1. 首先尝试直接解析JSON
            Map<String, Object> result = objectMapper.readValue(aiResponse, new TypeReference<Map<String, Object>>() {});
            
            String decision = (String) result.get("decision");
            BigDecimal confidence;
            Object confidenceObj = result.get("confidence");
            if (confidenceObj instanceof Number) {
                confidence = new BigDecimal(confidenceObj.toString());
            } else {
                confidence = new BigDecimal("0.0");
            }
            
            List<String> violations = (List<String>) result.get("violations");
            String reason = (String) result.get("reason");
            
            return ModerationResult.builder()
                    .contentType(contentType)
                    .decision(decision)
                    .confidence(confidence)
                    .violations(violations)
                    .reason(reason)
                    .build();
                    
        } catch (Exception e) {
            // 2. 如果JSON解析失败，尝试从文本中提取信息
            return parseTextResponse(aiResponse, contentType, e);
        }
    }

    /**
     * 从文本响应中解析审核结果
     */
    private ModerationResult parseTextResponse(String aiResponse, String contentType, Exception originalException) {
        try {
            String decision = "REVIEW";
            BigDecimal confidence = new BigDecimal("0.5");
            List<String> violations = new ArrayList<>();
            String reason = "AI响应格式异常，需要人工审核";
            
            // 尝试从文本中提取决策信息
            String lowerResponse = aiResponse.toLowerCase();
            if (lowerResponse.contains("pass") || lowerResponse.contains("通过")) {
                decision = "PASS";
                confidence = new BigDecimal("0.8");
                reason = "内容审核通过";
            } else if (lowerResponse.contains("reject") || lowerResponse.contains("拒绝") || 
                       lowerResponse.contains("违规") || lowerResponse.contains("敏感")) {
                decision = "REJECT";
                confidence = new BigDecimal("0.7");
                reason = "检测到违规内容";
                
                // 尝试提取违规类型
                if (lowerResponse.contains("政治")) violations.add("政治敏感");
                if (lowerResponse.contains("暴力")) violations.add("暴力血腥");
                if (lowerResponse.contains("色情")) violations.add("色情低俗");
                if (lowerResponse.contains("广告")) violations.add("商业广告");
                if (lowerResponse.contains("个人信息")) violations.add("个人信息泄露");
                if (lowerResponse.contains("版权")) violations.add("版权侵权");
                if (lowerResponse.contains("垃圾")) violations.add("垃圾信息");
            }
            
            // 如果没有提取到违规类型，添加默认类型
            if (violations.isEmpty() && "REJECT".equals(decision)) {
                violations.add("内容违规");
            }
            
            return ModerationResult.builder()
                    .contentType(contentType)
                    .decision(decision)
                    .confidence(confidence)
                    .violations(violations)
                    .reason(reason)
                    .build();
                    
        } catch (Exception e) {
            // 3. 如果文本解析也失败，返回降级结果
            return ModerationResult.builder()
                    .contentType(contentType)
                    .decision("REVIEW")
                    .confidence(new BigDecimal("0.0"))
                    .violations(Arrays.asList("AI解析失败"))
                    .reason("AI响应解析失败，需要人工审核")
                    .build();
        }
    }

    private ModerationResult createFallbackResult(String contentType, String errorMessage) {
        return ModerationResult.builder()
                .contentType(contentType)
                .decision("REVIEW")
                .confidence(new BigDecimal("0.0"))  // 使用 BigDecimal
                .violations(Arrays.asList("审核服务异常"))
                .reason("审核服务异常：" + errorMessage)
                .build();
    }

    // 内部数据类
    public static class ModerationResult {
        private String contentId;
        private String contentType;
        private String decision; // PASS/REJECT/REVIEW
        private BigDecimal confidence;  // 改为 BigDecimal
        private List<String> violations;
        private String reason;

        // 构建器模式
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private ModerationResult result = new ModerationResult();

            public Builder contentId(String contentId) {
                result.contentId = contentId;
                return this;
            }

            public Builder contentType(String contentType) {
                result.contentType = contentType;
                return this;
            }

            public Builder decision(String decision) {
                result.decision = decision;
                return this;
            }

            // 修正这里：改为接受 BigDecimal 参数
            public Builder confidence(BigDecimal confidence) {
                result.confidence = confidence;
                return this;
            }

            // 添加一个重载方法，接受 Double 参数并转换为 BigDecimal
            public Builder confidence(Double confidence) {
                result.confidence = confidence != null ? new BigDecimal(confidence.toString()) : new BigDecimal("0.0");
                return this;
            }

            public Builder violations(List<String> violations) {
                result.violations = violations;
                return this;
            }

            public Builder reason(String reason) {
                result.reason = reason;
                return this;
            }

            public ModerationResult build() {
                return result;
            }
        }

        // getters
        public String getContentId() { return contentId; }
        public String getContentType() { return contentType; }
        public String getDecision() { return decision; }
        public BigDecimal getConfidence() { return confidence; }  // 返回 BigDecimal
        public List<String> getViolations() { return violations; }
        public String getReason() { return reason; }

        public void setContentId(String contentId) { this.contentId = contentId; }
    }

    public static class ContentItem {
        private String contentId;
        private String contentType;
        private String title;
        private String content;
        private String category;

        // 构造函数
        public ContentItem(String contentId, String contentType, String title, String content, String category) {
            this.contentId = contentId;
            this.contentType = contentType;
            this.title = title;
            this.content = content;
            this.category = category;
        }

        // getters
        public String getContentId() { return contentId; }
        public String getContentType() { return contentType; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getCategory() { return category; }
    }
}