package com.homestay.common.utils;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Security工具类
 */
@Component
public class SecurityUtil {

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     * @throws BusinessException 如果用户未登录
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        }
        
        throw new BusinessException(ResultCode.UNAUTHORIZED);
    }

    /**
     * 判断用户是否已登录
     *
     * @return true:已登录 false:未登录
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * 获取当前用户的认证信息
     *
     * @return Authentication对象
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
} 