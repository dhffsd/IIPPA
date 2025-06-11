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
public class UserInfoDTO {
    /**
     * 用户简要信息
     */
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatarUrl;
    private String avatarType;
    private String company;
    private String introduction;
    private String region;
    private String gender;
    private LocalDateTime createdAt; // LocalDateTime 类型
    private LocalDateTime updatedAt; // LocalDateTime 类型

    /**
     * 从实体创建DTO对象的静态工厂方法
     */
    public static UserInfoDTO fromEntity(Users user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .avatarType(user.getAvatarType())
                .company(user.getCompany())
                .introduction(user.getIntroduction())
                .region(user.getRegion())
                .gender(user.getGender())
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
