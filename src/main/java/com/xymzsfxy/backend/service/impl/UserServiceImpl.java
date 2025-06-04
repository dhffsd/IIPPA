package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 实现根据用户名查找
    @Override
    public List<User> findByUserName(String username, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<User> u = userMapper.findByUserName(username,offset,size);
        return u;
    }

    @Override
    public void register(String username, String password, String userPic, String email) {
        // 清理用户名：去除前后空格，去掉开头的逗号
        username = username.trim();
        if (username.startsWith(",")) {
            username = username.substring(1); // 去掉开头的逗号
        }

//    加密

//    添加
        userMapper.add(username,password,userPic,email);
    }

    @Override
    public List<User> getAllUserInfo(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return userMapper.getAllUserInfo(offset,size);

    }

    @Override
    public Long getInfoCount() {
        return userMapper.getInfoCount();
    }

    @Override
    public Long getNameCount(String username) {
        return userMapper.getNameCount(username);
    }

    @Override
    public User findByUserRegisterName(String username) {
        return userMapper.findByUserRegisterName(username);
    }
}
