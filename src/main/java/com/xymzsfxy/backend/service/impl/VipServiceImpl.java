package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.dto.UserActivePointsDTO;
import com.xymzsfxy.backend.dto.VipGetPointsDTO;
import com.xymzsfxy.backend.dto.VipPointsRecordDTO;
import com.xymzsfxy.backend.dto.VipPurchaseDTO;
import com.xymzsfxy.backend.entity.*;
import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.mapper.VipMapper;
import com.xymzsfxy.backend.service.VipService;
import com.xymzsfxy.backend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VipServiceImpl implements VipService {
    @Autowired
    private VipMapper vipMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserVip getUserVipInfo(String accessToken) {
        // 解析token
        Long userId = jwtUtils.getUserIdFromToken(accessToken);

        // 获取Vip信息

        return vipMapper.getUserVipInfo(userId);


    }

    @Override
    public boolean purchaseVip(String accessToken, VipPurchaseDTO vipPurchase) {
        // 解析token
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        // 查询会员等级信息
        VipLevels level = vipMapper.selectVipLevelById(vipPurchase.getLevelId());
        if (level == null) {
            throw new RuntimeException("会员等级不存在");
        }

        // 2. 计算总价
        BigDecimal totalPrice = level.getMonthlyPrice().multiply(BigDecimal.valueOf(vipPurchase.getDuration()));
        Long totalPoints = level.getMinPoints() * vipPurchase.getDuration(); // 每月成长值

        // 3. 生成订单
        VipOrders order = new VipOrders();
        order.setUserId(userId);
        order.setLevelId(vipPurchase.getLevelId());
        order.setOrderSn(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setDuration(vipPurchase.getDuration());
        order.setPaymentMethod(vipPurchase.getPaymentMethod());
        order.setOrderStatus("pending");
        order.setCreatedAt(LocalDateTime.now());
        // 4. 支付逻辑
        if ("points".equals(vipPurchase.getPaymentMethod())) {
            // 用积分购买
            Users user = userMapper.selectAvailablePointsById(userId);
            if (user.getAvailablePoints() == null || user.getAvailablePoints() < totalPoints) {
                throw new RuntimeException("积分不足");
            }
            // 扣除积分
            user.setAvailablePoints(user.getAvailablePoints() - totalPoints);
            userMapper.updateById(user,userId);

            // 订单状态直接设为已支付
            order.setPaymentAmount(BigDecimal.ZERO);
            order.setOrderStatus("paid");
            order.setPaymentTime(LocalDateTime.now());

            // 记录积分流水
            VipPointRecords record = new VipPointRecords();
            record.setUserId(userId);
            record.setPoints(-totalPoints);
            record.setSource("purchase");
            record.setDescription("积分兑换VIP会员");
            record.setCreatedAt(LocalDateTime.now());
            vipMapper.insert(record);
        } else {
            // 钱支付（微信/支付宝/Paypal）
            order.setPaymentAmount(totalPrice);
            // 实际支付流程
            // 这里只做演示，直接设为已支付
            order.setOrderStatus("paid");
            order.setPaymentTime(LocalDateTime.now());
        }
        vipMapper.insertOrder(order);

        // 5. 更新/插入user_vip
        UserVip userVip = vipMapper.selectByUserId(userId);
        userMapper.updateUserRole(userId);
        LocalDate newExpire = LocalDate.now().plusMonths(vipPurchase.getDuration());
        if (userVip == null) {
            userVip = new UserVip();
            userVip.setUserId(userId);
            userVip.setLevelId(vipPurchase.getLevelId());
            userVip.setStatus("active");
            userVip.setPoints(0L);
            userVip.setExpireDate(newExpire);
            vipMapper.insertUserVip(userVip);
        } else {
            userVip.setLevelId(vipPurchase.getLevelId());
            userVip.setStatus("active");
            userVip.setExpireDate(newExpire.isAfter(userVip.getExpireDate()) ? newExpire : userVip.getExpireDate().plusMonths(vipPurchase.getDuration()));
            vipMapper.updateById(userVip);
        }

        // 6. 记录成长值
//        VipPointRecords growRecord = new VipPointRecords();
//        growRecord.setUserId(userId);
//        growRecord.setPoints(totalPoints);
//        growRecord.setSource("purchase");
//        growRecord.setDescription("购买VIP会员获得成长值");
//        growRecord.setCreatedAt(LocalDateTime.now());
//        vipMapper.insert(growRecord);

        return true;
    }

    @Override
    public List<VipLevels> getAllVipLevels() {
        List<VipLevels> vipLevels = vipMapper.selectAllByNull();
        return vipLevels;

    }

    @Override
    public VipGetPointsDTO getUserVipPoints(String accessToken) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);

        return vipMapper.getUserVipPoints(userId);


    }

    @Override
    public UserActivePointsDTO getUserPoints(String accessToken) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);

        Users users = userMapper.selectAvailablePointsById(userId);

        return UserActivePointsDTO.fromEntity(users);
    }

    @Override
    public List<VipPointsRecordDTO> getVipPointsRecord(String accessToken) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);

        List<VipPointsRecordDTO> vipPointsRecordDTOS = vipMapper.selectPointsRecordById(userId);

        return vipPointsRecordDTOS;

    }
}
