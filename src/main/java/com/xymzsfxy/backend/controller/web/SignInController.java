package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/web/sign")
public class SignInController {
    @Autowired
    private SignInService signInService;

    // 签到
    @PostMapping("/clock")
    public Result signIn(
            @CookieValue(name = "access_token", required = false) String accessToken,
            @RequestParam(defaultValue = "false") boolean isRepair,
            @RequestParam(defaultValue = "10") Long availablePoints
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }
        boolean b = signInService.signIn(accessToken, isRepair,availablePoints);
        if(b){
            return Result.success();
        }else {
            return Result.badRequest("已经签到过了");
        }
    }


    // 连续签到
   @GetMapping("/streak")
    public Result getStreak(
           @CookieValue(name = "access_token", required = false) String accessToken
   ){
       if (accessToken == null) {
           return Result.badRequest("未登录");
       }
       int streak = signInService.getStreak(accessToken);
       return Result.success(streak);
   }

    // 获取本月签到的日期
    @GetMapping("/days")
    public Result<List<Date>> getSignInDays(
            @CookieValue(name = "access_token", required = false) String accessToken
    ){
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }
        YearMonth month = YearMonth.now();
        List<Date> signInDays = signInService.getSignInDays(accessToken, month);
        return Result.success(signInDays);
    }

}
