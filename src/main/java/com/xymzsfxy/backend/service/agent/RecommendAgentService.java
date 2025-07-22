package com.xymzsfxy.backend.service.agent;

import com.xymzsfxy.backend.entity.AssistantResponse;
import com.xymzsfxy.backend.entity.Product;
import com.xymzsfxy.backend.entity.Supplier;
import com.xymzsfxy.backend.service.ProductService;
import com.xymzsfxy.backend.service.SupplierService;
import com.xymzsfxy.backend.client.LlmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecommendAgentService {
    @Autowired
    private LlmClient llmClient;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;

    public AssistantResponse handle(String userInput) {
        String productName = extractProductName(userInput);
        List<Product> products = productService.FindByProductName(productName, 1, 10);
        List<Supplier> allSuppliers = supplierService.getAll();
        List<Map<String, Object>> supplierList = new ArrayList<>();

        if (!products.isEmpty() && !allSuppliers.isEmpty()) {
            for (Supplier supplier : allSuppliers) {
                Map<String, Object> row = Map.of(
                    "供应商名称", supplier.getName() != null ? supplier.getName() : "",
                    "联系方式", supplier.getContact() != null ? supplier.getContact() : "",
                    "评分", supplier.getRating() != null ? supplier.getRating() : "",
                    "地址", supplier.getAddress() != null ? supplier.getAddress() : ""
                );
                supplierList.add(row);
            }
            AssistantResponse response = new AssistantResponse();
            response.setType(AssistantResponse.ResponseType.TABLE);
            response.setTitle(productName + " 推荐供应商");
            response.setContent(Map.of(
                "columns", List.of("供应商名称", "联系方式", "评分", "地址"),
                "rows", supplierList
            ));
            return response;
        } else {
            String aiReply = llmClient.generateResponse(productName, List.of(
                Map.of("role", "system", "content", "你是智链工采的AI助手，专注于采购、供应链、比价、推荐、流程提醒等企业服务。你可以用专业、友好的语气为用户推荐优质供应商、解答采购相关问题。"),
                Map.of("role", "user", "content", String.valueOf(userInput))
            ));
            AssistantResponse response = new AssistantResponse();
            response.setType(AssistantResponse.ResponseType.TEXT);
            response.setTitle("推荐供应商");
            response.setContent(aiReply);
            return response;
        }
    }

    private String extractProductName(String userInput) {
        String prompt = "请从以下用户输入中提取最有可能的商品名称，直接返回商品名即可，不要多余内容。用户输入：" + userInput;
        String aiExtracted = llmClient.generateResponse(prompt, List.of(
            Map.of("role", "system", "content", "你是一个商品名抽取助手。"),
            Map.of("role", "user", "content", prompt)
        ));
        if (aiExtracted != null && !aiExtracted.isBlank() && !"系统繁忙，请稍后再试".equals(aiExtracted.trim())) {
            System.out.println("大模型抽取商品名: " + aiExtracted);
            return aiExtracted.trim();
        }
        return userInput;
    }
} 