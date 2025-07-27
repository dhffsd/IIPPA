package com.xymzsfxy.backend.service;

import com.xymzsfxy.backend.dto.UserInfoDTO;
import com.xymzsfxy.backend.entity.EmailVerificationTokens;
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
    UserInfoDTO getCurrentUser(String accessToken);
//  更新用户信息
    boolean updateUserInfo(String accessToken, UserInfoDTO userInfoDTO);
//  更新用户头像
    void updateAvatarById(String accessToken, String avatarUrl, String avatarType);
//  保存邮箱校验码
    void sendBindMailCode(String mail, String accessToken);
//  校验邮箱验证码
    Boolean bindMail(String accessToken, String mail, String code);

    Users findByEmail(String mail);


    void sendLoginMailCode(Long id, String mail);

    EmailVerificationTokens loginByMail(Long id, String code, String mail);
}
