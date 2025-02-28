package com.xymzsfxy.backend.controller;

import com.xymzsfxy.backend.controller.web.UserController;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    void register() {

        Result result = userController.register("wushen", "123456");
        // 验证结果
        assertNotNull(result);
        assertEquals(400, result.getCode()); // 确保状态码是 400
    }

    @Test
    void login() {
// 模拟用户数据
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("root");
//        user.setPassword("123456");

        // 调用登录接口
        Result<String> result = userController.login("root", "123456");

        // 验证结果
        assertNotNull(result);
        assertEquals(200, result.getCode()); // 确保状态码是 200
        assertNotNull(result.getData()); // 确保返回的 JWT 令牌不为空

        // 验证 JWT 令牌是否有效
        Map<String, Object> claims = JWTUtils.parseToken(result.getData());
        assertEquals(8, claims.get("userId")); // 确保 JWT 中包含正确的 userId
        assertEquals("root", claims.get("username")); // 确保 JWT 中包含正确的 username;

    }
}