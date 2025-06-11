package com.xymzsfxy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 使用新的Lambda DSL配置
        http
                // 禁用CSRF保护（使用新语法）
                .csrf(csrf -> csrf.disable())
                // 配置请求授权
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()  // 允许所有请求不认证
                );

        return http.build();
    }
}