package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
//    根据用户名查询
    User findByUserName(String username);
//    注册
    void register(String username, String password);
}
