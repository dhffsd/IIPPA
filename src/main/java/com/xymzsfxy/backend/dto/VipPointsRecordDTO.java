package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipPointsRecordDTO {
    /**
     * 变更值
     */
    private Long points;

    /**
     * 来源
     */
    private String source;

    /**
     * 说明
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

}
