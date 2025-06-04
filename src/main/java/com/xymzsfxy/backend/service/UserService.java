package com.xymzsfxy.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
//    根据用户名查询
    List<User> findByUserName(String username, Integer page, Integer size);
//    注册
    void register(String username, String password, String userPic, String email);
//    获取所有用户信息
    List<User> getAllUserInfo(Integer page, Integer size);
//  获取用户数量
    Long getInfoCount();
//  根据用户名获取数量
    Long getNameCount(String username);
// 查询
    User findByUserRegisterName(String username);
}
