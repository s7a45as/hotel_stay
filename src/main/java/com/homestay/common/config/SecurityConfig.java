package com.homestay.common.config;

import com.homestay.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//TODO:解除任何的访问限制
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    /**
     * 配置Spring Security过滤链
     *
     * 此方法定义了HTTP安全配置，用于保护Web应用程序它禁用了CSRF保护，设置了会话管理策略为无状态，
     * 并配置了所有请求的访问规则最后，它在特定过滤器之前添加了一个自定义的JWT身份验证过滤器
     *
     * @param http HttpSecurity实例，用于配置Web应用程序的安全设置
     * @return SecurityFilterChain对象，代表配置好的安全过滤链
     * @throws Exception 如果配置过程中发生错误
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // 禁用CSRF保护，通常用于不涉及会话管理的API场景
            .csrf(AbstractHttpConfigurer::disable)
            // 配置会话管理策略为无状态，意味着服务器不会跟踪客户端会话状态，适用于RESTful API
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 配置请求授权规则，允许所有请求无需身份验证
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            // 在UsernamePasswordAuthenticationFilter之前添加JWT身份验证过滤器，用于处理JWT令牌验证
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // 构建并返回配置好的SecurityFilterChain
            .build();
    }

    /**
     * 密码加密器
     * 使用BCrypt强哈希函数加密密码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}