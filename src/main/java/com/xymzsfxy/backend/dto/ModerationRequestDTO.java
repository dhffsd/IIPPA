// src/main/java/com/xymzsfxy/backend/dto/ModerationRequestDTO.java
package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModerationRequestDTO {
    private String contentId;           // 内容ID
    private String contentType;         // 内容类型 (article/comment)
    private String title;               // 内容标题
    private String content;             // 内容正文
    private Long authorId;              // 作者ID
    private String authorName;          // 作者用户名
    private String category;            // 内容分类
    private String moderationLevel;     // 审核级别 (low/medium/high)
    private Map<String, Object> extraInfo; // 扩展信息
    private String source;              // 请求来源
}