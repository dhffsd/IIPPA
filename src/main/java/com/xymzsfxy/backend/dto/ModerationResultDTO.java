// src/main/java/com/xymzsfxy/backend/dto/ModerationResultDTO.java
package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModerationResultDTO {
    private Long id;                    // 审核记录ID
    private String contentId;           // 内容ID
    private String contentType;         // 内容类型
    private String decision;            // 审核决定 (PASS/REJECT/REVIEW)
    private BigDecimal confidence;          // 置信度
    private List<String> violations;    // 违规类型列表
    private Map<String, Object> details; // 详细审核结果
    private String aiReason;            // AI审核原因
    private String manualReason;        // 人工审核原因
    private String status;              // 状态 (PENDING/APPROVED/REJECTED)
    private Long reviewerId;            // 审核员ID
    private Date createdAt;             // 创建时间
    private Date reviewedAt;            // 审核时间
}