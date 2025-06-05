package com.xymzsfxy.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration:7200000}") // 默认2小时
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration:604800000}") // 默认7天
    private long refreshTokenExpiration;

    @Value("${jwt.cookie.domain:}")
    private String cookieDomain;

    @Value("${jwt.cookie.secure:true}")
    private boolean cookieSecure;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        // 确保密钥足够长
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }
        signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims) {
        return buildToken(claims, accessTokenExpiration);
    }

    public String generateRefreshToken(Map<String, Object> claims) {
        return buildToken(claims, refreshTokenExpiration);
    }

    private String buildToken(Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public ResponseCookie createAccessTokenCookie(String token) {
        return buildCookie("access_token", token, accessTokenExpiration);
    }

    public ResponseCookie createRefreshTokenCookie(String token) {
        return buildCookie("refresh_token", token, refreshTokenExpiration);
    }

    public ResponseCookie createLogoutCookie(String name) {
        return buildCookie(name, "", 0);
    }

    private ResponseCookie buildCookie(String name, String value, long maxAgeMillis) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .domain(cookieDomain)
                .maxAge(maxAgeMillis / 1000) // 转换为秒
                .sameSite("Lax")
                .build();
    }

    public String getUsernameFromToken(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(accessToken)
                .getBody();
        return claims.get("username", String.class);
    }
}