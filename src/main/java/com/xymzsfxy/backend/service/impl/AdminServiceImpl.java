package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.mapper.AdminMapper;
import com.xymzsfxy.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin FindByUserName(String username) {
        Admin admin = adminMapper.FindByUserName(username);
        return admin;
    }

    @Override
    public void register(String username, String password, String email, String phone) {
        String role = "普通管理员";
        adminMapper.register(username,password,email,phone,role);

    }

    @Override
    public Admin GetAdminInfo(String username) {
        Admin adminInfo = adminMapper.GetAdminInfo(username);
        return adminInfo;
    }
}
