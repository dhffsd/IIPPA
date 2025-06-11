package com.xymzsfxy.backend.dto;

import com.xymzsfxy.backend.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    /**
     * 用户简要信息
     */
    private Long id;
    private String username;
    private String avatarUrl;
    private String avatarType;
    private LocalDateTime createdAt; // LocalDateTime 类型
    private LocalDateTime updatedAt; // LocalDateTime 类型

    /**
     * 从实体创建DTO对象的静态工厂方法
     */
    public static LoginDTO fromEntity(Users user) {
        return LoginDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarUrl(user.getAvatarUrl())
                .avatarType(user.getAvatarType())
                .createdAt(convertToLocalDateTime(user.getCreatedAt()))
                .updatedAt(convertToLocalDateTime(user.getUpdatedAt()))
                .build();
    }

    /**
     * 将数字值转换为布尔值
     */
    private static Boolean convertToBoolean(Number value) {
        if (value == null) return false;
        return value.intValue() != 0;
    }

    /**
     * 将 Date 转换为 LocalDateTime
     */
    private static LocalDateTime convertToLocalDateTime(java.util.Date date) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
