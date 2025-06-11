package com.xymzsfxy.backend.interceptors;

import com.xymzsfxy.backend.utils.JWTUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从Cookie中获取访问令牌
        String token = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "access_token".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                token = accessTokenCookie.get().getValue();
            }
        }

        // 令牌不存在
        if (token == null || token.isEmpty()) {
            sendUnauthorized(response, "未授权，请重新登录");
            return false;
        }

        // 验证令牌有效性
        if (!jwtUtils.validateToken(token)) {
            sendUnauthorized(response, "令牌无效或已过期");
            return false;
        }

        return true;
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"" + message + "\"}");
        response.getWriter().flush();
    }
}