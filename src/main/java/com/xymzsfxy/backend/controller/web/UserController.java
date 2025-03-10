package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.entity.User;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.UserService;
import com.xymzsfxy.backend.utils.JWTUtils;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{4,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User u = userService.findByUserName(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.badRequest("用户名已占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{4,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password ){
//        根据用户名查询用户
        User u = userService.findByUserName(username);
//        判断该用户是否存在
        if(u == null){
            return Result.notFound("该用户");
        }

//        判断密码是否正确
        if(password.equals(u.getPassword())){
            // 生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", u.getId()); // 将用户ID放入JWT
            claims.put("username", u.getUsername()); // 将用户名放入JWT
            String token = JWTUtils.genToken(claims); // 生成JWT令牌
            return Result.success(token); // 返回JWT令牌
        }else {
            return Result.badRequest("密码错误");
        }
    }
}
