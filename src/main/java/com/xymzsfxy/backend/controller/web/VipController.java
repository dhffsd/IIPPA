package com.xymzsfxy.backend.controller.web;


import com.xymzsfxy.backend.dto.UserActivePointsDTO;
import com.xymzsfxy.backend.dto.VipGetPointsDTO;
import com.xymzsfxy.backend.dto.VipPointsRecordDTO;
import com.xymzsfxy.backend.dto.VipPurchaseDTO;
import com.xymzsfxy.backend.entity.UserVip;
import com.xymzsfxy.backend.entity.VipLevels;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/vip")
public class VipController {
    @Autowired
    private VipService vipService;

    // 获取当前用户会员信息
    @GetMapping("/user")
    public Result getUserVipInfo(
            @CookieValue(name = "access_token", required = false) String accessToken
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }

        UserVip userVipInfo = vipService.getUserVipInfo(accessToken);
        return Result.success(userVipInfo);
    }


    // 获取所有会员等级
    @GetMapping("/level")
    public Result<List<VipLevels>> getAllVipLevels(){
        List<VipLevels> allVipLevels = vipService.getAllVipLevels();
        return Result.success(allVipLevels);
    }


    // 获取成长值记录
    @GetMapping("/points")
    public Result getUserVipPoints(
            @CookieValue(name = "access_token", required = false) String accessToken
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }
        VipGetPointsDTO userVipPoints = vipService.getUserVipPoints(accessToken);
        return Result.success(userVipPoints);
    }


    // 获取用户可用积分点
    @GetMapping("/userpoints")
    public Result<UserActivePointsDTO> getUserPoints(
            @CookieValue(name = "access_token", required = false) String accessToken
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }
        UserActivePointsDTO userPoints = vipService.getUserPoints(accessToken);
        return Result.success(userPoints);
    }


    // 购买/续费会员
    @PostMapping("/purchase")
    public Result purchaseVip(
            @CookieValue(name = "access_token", required = false) String accessToken,
            @RequestBody VipPurchaseDTO vipPurchase
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }

        boolean purchaseVip = vipService.purchaseVip(accessToken, vipPurchase);
        if(purchaseVip){
            return Result.success();
        }else {
            return Result.badRequest("购买失败");
        }
    }

    // 获取积分记录
    @GetMapping("/pointsrecord")
    public Result<List<VipPointsRecordDTO>> getVipPonitsRecord(
            @CookieValue(name = "access_token", required = false) String accessToken
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }

        List<VipPointsRecordDTO> vipPointsRecord = vipService.getVipPointsRecord(accessToken);

        return Result.success(vipPointsRecord);

    }

}
