package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.dto.LoginDTO;
import com.xymzsfxy.backend.dto.UserInfoDTO;
import com.xymzsfxy.backend.entity.EmailVerificationTokens;
import com.xymzsfxy.backend.entity.Users;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.UserService;
import com.xymzsfxy.backend.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.xymzsfxy.backend.utils.AliOssUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/web/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired(required = false)
    private JavaMailSender sender;

    @PostMapping("/register")
    public Result<?> register(@RequestParam @Pattern(regexp = "^\\S{4,16}$") String username,
                              @RequestParam String passwordHash) {
        Users u = userService.findByUserRegisterName(username);
        if (u == null) {
            userService.register(username, passwordHash);
            return Result.success();
        } else {
            return Result.badRequest("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<LoginDTO> login(
            @Pattern(regexp = "^\\S{4,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String passwordHash,
            HttpServletResponse response
    ) {
        Users u = userService.findByUserRegisterName(username);
        if (u == null) {
            return Result.notFound("该用户");
        }

        boolean isEqPassword = userService.passwordEq(passwordHash, username);
        Users byUserLoginName = userService.getByUserLoginName(username);

        if (!isEqPassword) {
            return Result.badRequest("密码错误");
        }

        // 生成JWT令牌并设置到Cookie
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", u.getId());
        claims.put("username", u.getUsername());

        String accessToken = jwtUtils.generateAccessToken(claims);
        String refreshToken = jwtUtils.generateRefreshToken(claims);

        // 设置HttpOnly Cookie
        response.addHeader("Set-Cookie", jwtUtils.createAccessTokenCookie(accessToken).toString());
        response.addHeader("Set-Cookie", jwtUtils.createRefreshTokenCookie(refreshToken).toString());

        LoginDTO loginResponse = LoginDTO.fromEntity(byUserLoginName);

        return Result.success(loginResponse);
    }

    // 登出接口
    @PostMapping("/logout")
    public Result<?> logout(HttpServletResponse response) {
        // 清除Cookie中的令牌
        response.addHeader("Set-Cookie", jwtUtils.createLogoutCookie("access_token").toString());
        response.addHeader("Set-Cookie", jwtUtils.createLogoutCookie("refresh_token").toString());
        return Result.success();
    }

    @GetMapping("/get")
    public Result<Result.PageData<List<Users>>> getAllUserInfo(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<Users> allUserInfo = userService.getAllUserInfo(page, size);
        Long infoCount = userService.getInfoCount();
        return Result.successWithPage(infoCount, allUserInfo);
    }

    @GetMapping("/getName")
    public Result<Result.PageData<List<Users>>> getName(
            @ModelAttribute Users user,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<Users> byUserName = userService.findByUserName(user.getUsername(), page, size);
        Long nameCount = userService.getNameCount(user.getUsername());
        return Result.successWithPage(nameCount, byUserName);
    }

    // 获取当前登录用户信息
    @GetMapping("/info")
    public Result<UserInfoDTO> getCurrentUser(
            @CookieValue(name = "access_token", required = false) String accessToken) {
        UserInfoDTO currentUser = userService.getCurrentUser(accessToken);

        if (currentUser == null) {
            return Result.badRequest("未登录或令牌无效");
        }

        return Result.success(currentUser);
    }

    // 更新用户信息
    @PutMapping("/update")
    public Result updateUserInfo(
            @ModelAttribute UserInfoDTO userInfoDTO,
            @CookieValue(name = "access_token", required = false) String accessToken) {
        boolean updatedUser = userService.updateUserInfo(accessToken,userInfoDTO);
        if (updatedUser) {
            return Result.badRequest("未登录或令牌无效");
        }else {
            return Result.success();
        }
    }


    // 邮箱验证码
    @PostMapping("/sendBindMailCode")
    public Result sendBindMailCode(@RequestParam String mail, @CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken == null) return Result.badRequest("未登录");

        userService.sendBindMailCode(mail,accessToken);
        return Result.success();
    }


    // 校验邮箱验证码绑定
    @PostMapping("/bindMail")
    public Result<?> bindMail(@RequestParam String mail, @RequestParam String code, @CookieValue(name = "access_token", required = false) String accessToken) {
        if (accessToken == null) return Result.badRequest("未登录");
        // 绑定邮箱
        Boolean aBoolean = userService.bindMail(accessToken, mail, code);
        if (aBoolean) {
            return Result.success();
        }else {
            return Result.badRequest("验证码无效或已过期");
        }
    }


    @PostMapping("/sendLoginMailCode")
    public Result sendLoginMailCode(@RequestParam String mail) {
        Users user = userService.findByEmail(mail);
        if (user == null) return Result.badRequest("邮箱未绑定账号");
        userService.sendLoginMailCode(user.getId(),mail);
        return Result.success();
    }


    @PostMapping("/loginByMail")
    public Result<LoginDTO> loginByMail(@RequestParam String mail, @RequestParam String code, HttpServletResponse response) {
        Users user = userService.findByEmail(mail);
        if (user == null) return Result.badRequest("邮箱未绑定账号");
        EmailVerificationTokens token = userService.loginByMail(user.getId(), code, mail);

        if (token == null || token.getExpiresAt().before(new Date())) {
            return Result.badRequest("验证码无效或已过期");
        }

        // 登录逻辑（生成JWT等）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        String accessToken = jwtUtils.generateAccessToken(claims);
        String refreshToken = jwtUtils.generateRefreshToken(claims);
        response.addHeader("Set-Cookie", jwtUtils.createAccessTokenCookie(accessToken).toString());
        response.addHeader("Set-Cookie", jwtUtils.createRefreshTokenCookie(refreshToken).toString());

        LoginDTO loginResponse = LoginDTO.fromEntity(user);

        return Result.success(loginResponse);
    }

    // 上传头像文件到OSS并返回URL
    @PostMapping("uploadAvatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            // 1. 初始化OSS客户端
            AliOssUtil.init();
            // 2. 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename != null && originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf('.')) : "";
            String objectName = "avatar/" + System.currentTimeMillis() + ext;
            // 3. 保存临时文件
            File tempFile = File.createTempFile("oss_avatar_", ext);
            file.transferTo(tempFile);
            // 4. 上传到OSS
            AliOssUtil.uploadFile(objectName, tempFile);
            // 5. 获取永久URL（可根据需要设置过期时间，这里假设1年）
            String url = AliOssUtil.getUrl(objectName, 3600 * 24 * 365).toString();
            // 6. 删除临时文件
            tempFile.delete();
            // 7. 关闭OSS客户端
            AliOssUtil.shutdown();
            return Result.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.badRequest("头像上传失败: " + e.getMessage());
        }
    }


    // 更新用户头像
    @PutMapping("updateAvatar")
    public Result updateAvatar(
            @RequestParam String avatarUrl,
            @RequestParam String avatarType, // "url" 或 "upload"
            @CookieValue(name = "access_token", required = false) String accessToken
    ) {
        if (accessToken == null) {
            return Result.badRequest("未登录");
        }
        userService.updateAvatarById(accessToken, avatarUrl, avatarType);
        return Result.success();
    }



}