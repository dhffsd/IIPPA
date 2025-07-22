package com.xymzsfxy.backend.service.agent;

import com.xymzsfxy.backend.entity.AssistantResponse;
import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.entity.History;
import com.xymzsfxy.backend.entity.Supplier;
import com.xymzsfxy.backend.service.ProductService;
import com.xymzsfxy.backend.service.HistoryService;
import com.xymzsfxy.backend.service.SupplierService;
import com.xymzsfxy.backend.client.LlmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PriceAgentService {
    @Autowired
    private ProductService productService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private LlmClient llmClient;

    public AssistantResponse comparePrices(String userInput) {
        // 1. 简单提取商品名（可用正则或大模型辅助）
        String productName = extractProductName(userInput);
        List<Product> products = productService.FindByProductName(productName, 1, 10);
        if (products.isEmpty()) {
            return buildTextResponse("未找到相关商品，请检查商品名称。");
        }
        Product product = products.get(0);

        // 2. 查本地价格历史
        List<History> allHistory = historyService.findAll();
        List<History> priceList = new ArrayList<>();
        for (History h : allHistory) {
            if (h.getProductId() != null && h.getProductId().equals(product.getId())) {
                priceList.add(h);
            }
        }
        if (priceList.isEmpty()) {
            return buildTextResponse("该商品暂无本地价格数据。");
        }

        // 3. 组装结构化比价表
        List<Map<String, Object>> rows = new ArrayList<>();
        for (History price : priceList) {
            Supplier supplier = supplierService.findBySupplierName(price.getSource());
            Map<String, Object> row = Map.of(
                "供应商", price.getSource() != null ? price.getSource() : "",
                "商品名称", product.getName() != null ? product.getName() : "",
                "价格(元)", price.getPrice() != null ? price.getPrice() : "",
                "物流费", price.getLogisticsCost() != null ? price.getLogisticsCost() : "",
                "综合成本", price.getTotalCost() != null ? price.getTotalCost() : "",
                "评分", (supplier != null && supplier.getRating() != null) ? supplier.getRating() : "",
                "历史最高价", price.getMaxPrice() != null ? price.getMaxPrice() : "",
                "历史最低价", price.getMinPrice() != null ? price.getMinPrice() : "",
                "采集时间", price.getCrawlTime() != null ? price.getCrawlTime() : ""
            );
            rows.add(row);
        }

        AssistantResponse response = new AssistantResponse();
        response.setType(AssistantResponse.ResponseType.TABLE);
        response.setTitle(product.getName() + " 价格对比");
        response.setContent(Map.of(
            "columns", List.of("供应商", "商品名称", "价格(元)", "物流费", "综合成本", "评分", "历史最高价", "历史最低价", "采集时间"),
            "rows", rows
        ));
        return response;
    }

    private String extractProductName(String userInput) {
        // 1. 让大模型抽取商品名
        String prompt = "请从以下用户输入中提取最有可能的商品名称，直接返回商品名即可，不要多余内容。用户输入：" + userInput;
        String aiExtracted = llmClient.generateResponse(prompt, List.of(
            Map.of("role", "system", "content", "你是一个商品名抽取助手。"),
            Map.of("role", "user", "content", prompt)
        ));
        if (aiExtracted != null && !aiExtracted.isBlank()) {
            System.out.println("大模型抽取商品名: " + aiExtracted);
            return aiExtracted.trim();
        }
        // 2. 兜底用原有模糊匹配
        String input = userInput.trim().toLowerCase();
        List<Product> allProducts = productService.get(1, 1000);
        for (Product p : allProducts) {
            String productName = p.getName().trim().toLowerCase();
            if (input.contains(productName) || productName.contains(input)) {
                System.out.println("全名匹配成功: " + p.getName());
                return p.getName();
            }
            for (String word : productName.split("[\\s、，,]")) {
                if (!word.isEmpty() && input.contains(word)) {
                    System.out.println("关键词模糊匹配成功: " + p.getName() + "，关键词: " + word);
                    return p.getName();
                }
            }
        }
        System.out.println("未匹配到商品名，返回原文: " + userInput);
        return userInput;
    }

    private AssistantResponse buildTextResponse(String text) {
        AssistantResponse response = new AssistantResponse();
        response.setType(AssistantResponse.ResponseType.TEXT);
        response.setTitle("比价结果");
        response.setContent(text);
        return response;
    }
} 