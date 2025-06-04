// HistoryDTO 类
package com.xymzsfxy.backend.dto;

public class HistoryDTO {
    private History history;
    private String productName;

    // 构造函数
    public HistoryDTO(History history, String productName) {
        this.history = history;
        this.productName = productName;
    }

    // getters and setters
    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}