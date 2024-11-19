package com.homestay.common.security;

import com.homestay.common.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            
            String token = authHeader.substring(7);
            
            // 检查token是否在黑名单中
            String blacklistKey = String.format("token:blacklist:%s", token);
            if (Boolean.TRUE.equals(redisTemplate.hasKey(blacklistKey))) {
                throw new JwtException("Token已失效");
            }


            // 从Redis中获取token信息
            String tokenKey = String.format("token:auth:%s", token);
            System.out.println(token);
            Object tokenInfo = redisTemplate.opsForValue().get(tokenKey);
            
            if (tokenInfo == null) {
                throw new JwtException("Token不存在或已过期");
            }
            
            // 验证token
            if (jwtUtil.validateToken(token)) {
                // 从Redis中获取userId
                Map<String, Object> tokenMap = (Map<String, Object>) tokenInfo;
                Long userId = Long.valueOf(tokenMap.get("userId").toString());

                // 更新最后访问时间
                tokenMap.put("lastAccessTime", LocalDateTime.now().toString());
                redisTemplate.opsForValue().set(tokenKey, tokenMap, 2, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(String.format("token:user:%d", userId), tokenMap, 2, TimeUnit.DAYS);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                    
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                log.debug("Token验证成功，用户ID: {}", userId);
            }
            
        } catch (Exception e) {
            log.error("Token验证失败: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }
} 