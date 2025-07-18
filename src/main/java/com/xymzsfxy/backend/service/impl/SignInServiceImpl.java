package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.SignIn;
import com.xymzsfxy.backend.mapper.SignInMapper;
import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.service.SignInService;
import com.xymzsfxy.backend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean signIn(String accessToken, boolean isRepair, Long availablePoints) {
        // 解析token
        Long userIdFromToken = jwtUtils.getUserIdFromToken(accessToken);

        // 判断是否已经签到
        LocalDate today = LocalDate.now();
        Long aLong = signInMapper.existsByUserId(userIdFromToken, today);
        if(aLong > 0 && !isRepair){
            return false;
        }
        LocalDateTime signInTime = LocalDateTime.now();
        Long isR = isRepair ? 0 : availablePoints;
        int repair = isRepair ? 1 : 0;
        // 签到
        signInMapper.insert(userIdFromToken,today,signInTime,isR,repair);
        userMapper.updatePoints(userIdFromToken,availablePoints);
        return true;
    }

    @Override
    public List<Date> getSignInDays(String accessToken, YearMonth month) {
        // 解析token
        Long userIdFromToken = jwtUtils.getUserIdFromToken(accessToken);

        // 解析时间
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        // 获取本月签到的日期
        List<SignIn> signIns = signInMapper.selectByUserId(userIdFromToken, start, end);
        return  signIns.stream().map(SignIn::getSignInDate).collect(Collectors.toList());

    }

    @Override
    public int getStreak(String accessToken) {
        // 解析token
        Long userIdFromToken = jwtUtils.getUserIdFromToken(accessToken);

        // 查询连续签到的次数
        LocalDate today = LocalDate.now();
        int streak = 0;
        LocalDate date = today;
        while (signInMapper.existsByUserId(userIdFromToken, date) > 0){
            streak++;
            date = date.minusDays(1);
        }
        return streak;

    }
}
