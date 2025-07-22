package com.xymzsfxy.backend.service.orchestrator;

import com.xymzsfxy.backend.entity.AssistantResponse;
import com.xymzsfxy.backend.controller.AssistantController.Message;
import com.xymzsfxy.backend.service.agent.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrchestratorService {

    @Autowired private QaAgentService qaAgent;
    @Autowired private RecommendAgentService recommendAgent;
    @Autowired private PriceAgentService priceAgent;
    @Autowired private OrderAgentService orderAgent;
    @Autowired private DataAgentService dataAgent;
    @Autowired private ReminderAgentService reminderAgent;

    public AssistantResponse processMessage(List<Message> messages, String sessionId) {
        String userInput = messages.get(messages.size() - 1).getContent();
        String intent = detectIntent(userInput, messages);
        log.info("Detected intent: {}", intent);

        return switch (intent) {
            case "PRODUCT_QUERY" -> qaAgent.handleProductQuery(userInput, sessionId);
            case "RECOMMENDATION" -> recommendAgent.handle(userInput);
            case "PRICE_COMPARE" -> priceAgent.comparePrices(userInput);
            case "ORDER_CREATE" -> orderAgent.createDraftOrder(userInput);
            case "DATA_ANALYSIS" -> dataAgent.analyzePurchasingData(userInput);
            case "PROCESS_REMINDER" -> reminderAgent.checkProcessStatus(userInput);
            default -> handleFallback(userInput, intent, sessionId);
        };
    }

    private String detectIntent(String userInput, List<Message> context) {
        // 可用LLM、规则、关键词等
        if (userInput.contains("比价")) return "PRICE_COMPARE";
        if (userInput.contains("推荐")) return "RECOMMENDATION";
        if (userInput.matches(".*(采购单|下单|订购).*")) return "ORDER_CREATE";
        if (userInput.matches(".*(统计|分析|报表).*")) return "DATA_ANALYSIS";
        if (userInput.matches(".*(提醒|待办|审批|进度).*")) return "PROCESS_REMINDER";
        return "QA";
    }

    private AssistantResponse handleFallback(String input, String intent, String sessionId) {
        AssistantResponse response = qaAgent.handle(input, sessionId);
        AssistantResponse.Action action = new AssistantResponse.Action();
        action.setName("人工客服");
        action.setType("transfer_service");
        response.getActions().add(action);
        return response;
    }
} 