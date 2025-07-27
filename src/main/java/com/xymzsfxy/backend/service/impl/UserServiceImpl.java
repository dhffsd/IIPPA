package com.xymzsfxy.backend.service.impl;

import com.xymzsfxy.backend.dto.UserInfoDTO;
import com.xymzsfxy.backend.entity.EmailVerificationTokens;
import com.xymzsfxy.backend.entity.Users;
import com.xymzsfxy.backend.mapper.UserMapper;
import com.xymzsfxy.backend.service.UserService;
import com.xymzsfxy.backend.utils.JWTUtils;
import com.xymzsfxy.backend.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private JWTUtils jwtUtils;


    @Autowired(required = false)
    private JavaMailSender sender;

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

    @Override
    public void updateAvatarById(String accessToken, String avatarUrl, String avatarType) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        userMapper.updateAvatar(userId, avatarUrl, avatarType);
    }

    @Override
    public void sendBindMailCode(String mail, String accessToken) {

        // 生成验证码
        String code = String.valueOf(100000 + new Random().nextInt(900000));
        // 发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮箱绑定验证码");
        message.setText("您的邮箱绑定验证码为：" + code + "，有效期2分钟。");
        message.setTo(mail);
        message.setFrom("3170195273@qq.com");
        sender.send(message);

        // 保存到数据库
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        Date exdate = new Date(System.currentTimeMillis() + 2 * 60 * 1000);
        // 保存token到数据库
        userMapper.saveMailToken(userId, code, exdate);

    }

    @Override
    public Boolean bindMail(String accessToken, String mail, String code) {
        Long userId = jwtUtils.getUserIdFromToken(accessToken);
        EmailVerificationTokens token = userMapper.findTopByUserIdAndTokenOrderByCreatedAtDesc(userId, code);
        if (token == null || token.getExpiresAt().before(new Date())) {
            return false;
        }
        userMapper.setMailByUserId(mail,userId);
        return true;
    }

    @Override
    public Users findByEmail(String mail) {
        Users users = userMapper.selectEmail(mail);
        return users;
    }

    @Override
    public void sendLoginMailCode(Long id, String mail) {
        String code = String.valueOf(100000 + new Random().nextInt(900000));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮箱登录验证码");
        message.setText("您的登录验证码为：" + code + "，有效期2分钟。");
        message.setTo(mail);
        message.setFrom("3170195273@qq.com");
        sender.send(message);
        Date exdate = new Date(System.currentTimeMillis() + 2 * 60 * 1000);

        userMapper.insertLoginMailCode(id, code, exdate);
    }

    @Override
    public EmailVerificationTokens loginByMail(Long id, String code, String mail) {

        EmailVerificationTokens token = userMapper.findTopByUserIdAndTokenOrderByCreatedAtDesc(id, code);

        return token;
    }
}
