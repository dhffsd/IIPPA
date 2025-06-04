package com.xymzsfxy.backend.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    // 根据管理名称查询
    Admin FindByUserName(String username);
    // 注册
    void register(String username, String password, String email, String phone);
    // 根据管理名称获取管理员信息
    Admin GetAdminInfo(String username);
}
