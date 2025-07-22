package com.xymzsfxy.backend.dto.AssistantDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    // 会话ID - 用于标识独立对话
    private String sessionId;

    // 消息历史 - 包含系统、用户和助手的消息
    private List<MessageDTO> messages;
}
