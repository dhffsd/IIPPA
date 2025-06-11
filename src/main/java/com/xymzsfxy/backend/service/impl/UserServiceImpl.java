package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.dto.UserInfoDTO;
import com.xymzsfxy.backend.entity.Users;
import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.service.UserService;
import com.xymzsfxy.backend.utils.JWTUtils;
import com.xymzsfxy.backend.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private JWTUtils jwtUtils;

    // 实现根据用户名查找
    @Override
    public List<Users> findByUserName(String username, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Users> u = userMapper.findByUserName(username,offset,size);
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
        String passwordHash = passwordUtils.hashPassword(password);

//    添加
        userMapper.add(username,passwordHash);
    }

    @Override
    public List<Users> getAllUserInfo(Integer page, Integer size) {
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
    public Users findByUserRegisterName(String username) {
        return userMapper.findByUserRegisterName(username);
    }

    @Override
    public boolean passwordEq(String password,String username) {
//        获取该用户hash密码
        String password1 = userMapper.getPassword(username);
//        解密
        return passwordUtils.matches(password,password1);
    }

    @Override
    public Users getByUserLoginName(String username) {
        return userMapper.getUserInfo(username);
    }

    @Override
    public UserInfoDTO getCurrentUser(String accessToken) {
        if (accessToken != null && jwtUtils.validateToken(accessToken)) {
            String username = jwtUtils.getUsernameFromToken(accessToken);
            Users user = userMapper.getUserInfo(username);
            return UserInfoDTO.fromEntity(user);
        }
        return null;
    }

    @Override
    public boolean updateUserInfo(String accessToken, UserInfoDTO userInfoDTO) {
        // 验证并获取当前用户ID
        if (accessToken != null && jwtUtils.validateToken(accessToken)) {
            Long userId = jwtUtils.getUserIdFromToken(accessToken);
            userMapper.updateUserInfo(userId, userInfoDTO.getUsername(),
                    userInfoDTO.getEmail(), userInfoDTO.getPhone(), userInfoDTO.getCompany(),
                    userInfoDTO.getIntroduction(), userInfoDTO.getRegion(), userInfoDTO.getGender());
            return true;
        }
        return false;


    }
}
