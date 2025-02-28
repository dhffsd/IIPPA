package com.xymzsfxy.backend.interceptors;

import com.xymzsfxy.backend.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
// 用户登录模块
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 Authorization 字段
        String token = request.getHeader("Authorization");

        // 检查令牌是否存在
        if (token == null || !token.startsWith("Bearer ")) {
            // 令牌不存在或格式不正确，返回 401 未授权
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未授权，请先登录");
            return false;
        }

        // 去掉 "Bearer " 前缀，获取实际的 JWT 令牌
        token = token.substring(7);

        // 验证令牌是否有效
        try {
            JWTUtils.parseToken(token); // 解析并验证令牌
            return true; // 令牌有效，放行请求
        } catch (Exception e) {
            // 令牌无效，返回 401 未授权
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("令牌无效，请重新登录");
            return false;
        }
    }
}