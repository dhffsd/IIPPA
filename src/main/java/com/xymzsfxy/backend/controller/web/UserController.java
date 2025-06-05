package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.dto.LoginDTO;
import com.xymzsfxy.backend.entity.Users;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.UserService;
import com.xymzsfxy.backend.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/register")
    public Result<?> register(@RequestBody Users user) {
        Users u = userService.findByUserRegisterName(user.getUsername());
        if (u == null) {
            userService.register(user.getUsername(), user.getPasswordHash());
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
    public Result<LoginDTO> getCurrentUser(
            @CookieValue(name = "access_token", required = false) String accessToken) {
        LoginDTO currentUser = userService.getCurrentUser(accessToken);

        if (currentUser == null) {
            return Result.badRequest("未登录或令牌无效");
        }

        return Result.success(currentUser);
    }


}