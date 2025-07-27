package com.xymzsfxy.backend.dto;

import com.xymzsfxy.backend.entity.UserMessage;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消息事件DTO - 用于Kafka消息传递
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEventDTO {
    private Long id;
    private Long receiverId;
    private Long senderId;
    private String type;
    private String title;
    private String content;
    private Integer isRead;
    private String relatedEntityType;
    private Long relatedEntityId;
    private LocalDateTime createdAt;

    /**
     * 从实体类转换为事件DTO
     */
    public static MessageEventDTO fromEntity(UserMessage message) {
        return MessageEventDTO.builder()
                .id(message.getId())
                .receiverId(message.getReceiverId())
                .senderId(message.getSenderId())
                .type(message.getType())
                .title(generateTitle(message))
                .content(message.getContent())
                .isRead(message.getIsRead())
                .relatedEntityType(message.getRelatedEntityType())
                .relatedEntityId(message.getRelatedEntityId())
                .createdAt(message.getCreatedAt())
                .build();
    }

    /**
     * 根据消息类型生成标题
     */
    private static String generateTitle(UserMessage message) {
        switch (message.getType()) {
            case "REPLY": return "新回复";
            case "SYSTEM": return "系统通知";
            // case "LIKE": return "点赞通知";
            // case "MENTION": return "@提及";
            default: return "新消息";
        }
    }
}