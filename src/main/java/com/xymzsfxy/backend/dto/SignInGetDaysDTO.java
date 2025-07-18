package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInGetDaysDTO {
    private Long userId;
    private Date signInDate;
    private Date signInTime;
    private String remark;
}
