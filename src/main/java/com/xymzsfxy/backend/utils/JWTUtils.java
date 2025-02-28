package com.xymzsfxy.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JWTUtils {

    // 密钥，用于签名和验证JWT
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT有效期，单位：毫秒
    private static final long EXPIRATION_TIME = 86400000; // 24小时

    /**
     * 生成JWT令牌
     *
     * @param claims 包含的用户信息
     * @return JWT令牌
     */
    public static String genToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // 设置用户信息
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .signWith(SECRET_KEY) // 使用密钥签名
                .compact(); // 生成JWT字符串
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT令牌
     * @return 包含的用户信息
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 设置签名密钥
                .build()
                .parseClaimsJws(token) // 解析JWT
                .getBody(); // 获取用户信息
    }

    /**
     * 验证JWT令牌是否有效
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}