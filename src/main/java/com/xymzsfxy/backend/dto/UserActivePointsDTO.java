package com.xymzsfxy.backend.dto;

import com.xymzsfxy.backend.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActivePointsDTO {
    private Long availablePoints;

    public static UserActivePointsDTO fromEntity(Users users){
        return UserActivePointsDTO.builder().availablePoints(users.getAvailablePoints()).build();
    }
}
