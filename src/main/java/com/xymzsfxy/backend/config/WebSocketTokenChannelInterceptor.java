package com.xymzsfxy.backend.config;

import com.xymzsfxy.backend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Component
public class WebSocketTokenChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String token = null;
//            String headerToken = accessor.getFirstNativeHeader("Authorization");
//            if (headerToken != null) {
//                token = headerToken;
//                System.out.println("[WebSocket] 从header获取token: " + token);
//            }
//            String cookieHeader = accessor.getFirstNativeHeader("Cookie");
//            if (token == null && cookieHeader != null) {
//                for (String c : cookieHeader.split(";")) {
//                    c = c.trim();
//                    if (c.startsWith("access_token=")) {
//                        token = c.substring("access_token=".length());
//                        System.out.println("[WebSocket] 从cookie获取token: " + token);
//                        break;
//                    }
//                }
//            }
//            System.out.println("[WebSocket] CONNECT阶段获取到token: " + token);
//            if (token == null || !jwtUtils.validateToken(token)) {
//                System.out.println("[WebSocket] token校验失败: " + token);
//                throw new IllegalArgumentException("WebSocket未授权或token无效");
//            }
//            System.out.println("[WebSocket] token校验通过: " + token);
//            final String finalToken = token;
//            accessor.setUser(new java.security.Principal() {
//                @Override
//                public String getName() {
//                    return jwtUtils.getUsernameFromToken(finalToken);
//                }
//            });
//        }
        return message;
    }
} 