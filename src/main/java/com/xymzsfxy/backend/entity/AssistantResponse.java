package com.xymzsfxy.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssistantResponse {
    private ResponseType type;  // 响应类型
    private String title;       // 标题
    private Object content;     // 内容（结构化数据）
    private List<Action> actions = new ArrayList<>(); // 可交互操作

    public enum ResponseType {
        TEXT, TABLE, LIST, CHART, FORM, PROGRESS
    }

    @Data
    public static class Action {
        private String name;   // 操作名称
        private String type;   // 操作类型（api_call/open_url/confirm等）
        private String target; // 调用目标
        private Object params; // 请求参数
    }
} 