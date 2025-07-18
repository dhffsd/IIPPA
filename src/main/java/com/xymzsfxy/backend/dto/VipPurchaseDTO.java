package com.xymzsfxy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipPurchaseDTO {
    private Integer levelId;        // 会员等级ID
    private Integer duration;       // 购买时长（月）
    private String paymentMethod;   // 支付方式: wechat/alipay/paypal/points
}
