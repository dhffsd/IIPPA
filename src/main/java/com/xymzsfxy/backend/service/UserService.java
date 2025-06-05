package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.LoginDTO;
import com.xymzsfxy.backend.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
//    根据用户名查询
    List<Users> findByUserName(String username, Integer page, Integer size);
//    注册
    void register(String username, String password);
//    获取所有用户信息
    List<Users> getAllUserInfo(Integer page, Integer size);
//  获取用户数量
    Long getInfoCount();
//  根据用户名获取数量
    Long getNameCount(String username);
// 查询
    Users findByUserRegisterName(String username);
//  校验密码
    boolean passwordEq(String password,String username);
//  根据用户名获取个人信息
    Users getByUserLoginName(String username);
//  用户认证
    LoginDTO getCurrentUser(String accessToken);
}
