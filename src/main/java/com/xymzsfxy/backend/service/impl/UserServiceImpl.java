package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.entity.User;
import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 实现根据用户名查找
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 清理用户名：去除前后空格，去掉开头的逗号
        username = username.trim();
        if (username.startsWith(",")) {
            username = username.substring(1); // 去掉开头的逗号
        }

//    加密

//    添加
        userMapper.add(username,password);
    }
}
