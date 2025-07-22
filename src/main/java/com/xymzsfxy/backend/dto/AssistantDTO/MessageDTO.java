package com.xymzsfxy.backend.dto.AssistantDTO;

import lombok.Data;

import java.util.Map;

@Data
public class MessageDTO {
    // 角色：user(用户), system(系统), assistant(助手)
    private String role;

    // 消息内容
    private String content;

    // 元数据 - 可包含图片、文件等附加信息
    private Map<String, Object> metadata;
}
