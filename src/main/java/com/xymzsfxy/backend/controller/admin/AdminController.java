package com.xymzsfxy.backend.controller.admin;

import com.xymzsfxy.backend.entity.Admin;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.AdminService;
import com.xymzsfxy.backend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //    管理员注册
    @PostMapping("/register")
    public Result register(String username, String password, String email, String phone) {
        // 查找管理员是否存在
        Admin admin = adminService.FindByUserName(username);
        if (admin != null) {
            return Result.badRequest("管理员已存在");
        }
        // 注册
        adminService.register(username, password, email, phone);
        return Result.success();
    }

    @PostMapping("/login")
    //    管理员登录
    public Result<String> login(String username, String password) {
        // 判断用户是否存在
        Admin admin = adminService.FindByUserName(username);
        if(admin == null){
            return Result.notFound("用户");
        }

        // 生成令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("adminId",admin.getId());
        claims.put("adminName",admin.getUsername());
        String token = JWTUtils.genToken(claims);
        // 登录
        return Result.success(token);
    }

    // 获取管理员详细信息
    @GetMapping("/getInfo")
    public Result<Admin> getAdminInfo(String username) {
        // 查询用户名是否存在
        Admin admin = adminService.FindByUserName(username);
        if(admin == null){
            return Result.notFound("用户");
        }
        // 获取管理员信息
        Admin adminInfo = adminService.GetAdminInfo(username);
        System.out.println(adminInfo.getCreatedTime());

        return Result.success(adminInfo);
    }
}
