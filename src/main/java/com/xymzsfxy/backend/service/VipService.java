package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.UserActivePointsDTO;
import com.xymzsfxy.backend.dto.VipGetPointsDTO;
import com.xymzsfxy.backend.dto.VipPointsRecordDTO;
import com.xymzsfxy.backend.dto.VipPurchaseDTO;
import com.xymzsfxy.backend.entity.UserVip;
import com.xymzsfxy.backend.entity.VipLevels;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VipService {
    UserVip getUserVipInfo(String accessToken);

    boolean purchaseVip(String accessToken, VipPurchaseDTO vipPurchase);

    List<VipLevels> getAllVipLevels();

    VipGetPointsDTO getUserVipPoints(String accessToken);

    UserActivePointsDTO getUserPoints(String accessToken);

    List<VipPointsRecordDTO> getVipPointsRecord(String accessToken);
}
