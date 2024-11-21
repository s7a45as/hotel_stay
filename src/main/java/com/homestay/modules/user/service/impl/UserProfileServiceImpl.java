package com.homestay.modules.user.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.user.dto.UserProfileDTO;
import com.homestay.modules.user.entity.UserInfo;
import com.homestay.modules.user.mapper.UserMapper;
import com.homestay.modules.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UploadUtils uploadUtils;

    @Override
    public UserProfileDTO getUserProfile() {
        // 获取当前登录用户ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        
        Long userId;
        Object principal = authentication.getPrincipal();
        if (principal instanceof Long) {
            userId = (Long) principal;
        } else if (principal instanceof String) {
            try {
                userId = Long.parseLong((String) principal);
            } catch (NumberFormatException e) {
                throw new BusinessException(ResultCode.UNAUTHORIZED);
            }
        } else {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        UserInfo user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        
        UserProfileDTO profileDTO = new UserProfileDTO();
        BeanUtils.copyProperties(user, profileDTO);
        return profileDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfile(UserProfileDTO userProfileDTO) {
        log.info("开始更新用户信息: {}", userProfileDTO);
        
        // 获取当前用户ID
        Long userId = getCurrentUserId();
        log.debug("当前用户ID: {}", userId);
        
        // 查询用户信息
        UserInfo user = userMapper.selectById(userId);
        if (user == null) {
            log.error("用户不存在, userId: {}", userId);
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        
        // 验证手机号格式
        if (userProfileDTO.getPhone() != null && !userProfileDTO.getPhone().matches("^1[3-9]\\d{9}$")) {
            log.error("手机号格式错误: {}", userProfileDTO.getPhone());
            throw new BusinessException(ResultCode.PARAM_ERROR, "手机号格式错误");
        }
        
        // 验证邮箱格式
        if (userProfileDTO.getEmail() != null && !userProfileDTO.getEmail().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            log.error("邮箱格式错误: {}", userProfileDTO.getEmail());
            throw new BusinessException(ResultCode.PARAM_ERROR, "邮箱格式错误");
        }
        
        // 验证性别
        if (userProfileDTO.getGender() != null && 
            !userProfileDTO.getGender().matches("^(male|female|other)$")) {
            log.error("性别格式错误: {}", userProfileDTO.getGender());
            throw new BusinessException(ResultCode.PARAM_ERROR, "性别只能是male、female或other");
        }
        
        // 验证生日
        if (userProfileDTO.getBirthday() != null && 
            userProfileDTO.getBirthday().isAfter(LocalDateTime.now())) {
            log.error("生日不能是未来时间: {}", userProfileDTO.getBirthday());
            throw new BusinessException(ResultCode.PARAM_ERROR, "生日不能是未来时间");
        }
        
        try {
            // 保存原始值，用于日志记录
            String oldNickname = user.getNickname();
            String oldPhone = user.getPhone();
            String oldEmail = user.getEmail();
            
            // 设置要更新的字段
            if (userProfileDTO.getNickname() != null) {
                user.setNickname(userProfileDTO.getNickname());
            }
            if (userProfileDTO.getPhone() != null) {
                user.setPhone(userProfileDTO.getPhone());
            }
            if (userProfileDTO.getEmail() != null) {
                user.setEmail(userProfileDTO.getEmail());
            }
            if (userProfileDTO.getGender() != null) {
                user.setGender(userProfileDTO.getGender());
            }
            if (userProfileDTO.getBirthday() != null) {
                user.setBirthday(userProfileDTO.getBirthday());
            }
            if (userProfileDTO.getAddress() != null) {
                user.setAddress(userProfileDTO.getAddress());
            }
            if (userProfileDTO.getBio() != null) {
                user.setBio(userProfileDTO.getBio());
            }
            if (userProfileDTO.getAvatar() != null) {
                user.setAvatar(userProfileDTO.getAvatar());
            }
            
            // 更新用户信息
            int rows = userMapper.updateById(user);
            log.info("更新影响行数: {}", rows);
            
            if (rows <= 0) {
                throw new BusinessException(ResultCode.UPDATE_ERROR, "更新用户信息失败");
            }
            
            // 记录更新日志
            log.info("用户信息更新成功, userId: {}, 昵称: {} -> {}, 手机: {} -> {}, 邮箱: {} -> {}", 
                    userId, oldNickname, user.getNickname(), 
                    oldPhone, user.getPhone(), 
                    oldEmail, user.getEmail());
                
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            throw new BusinessException(ResultCode.UPDATE_ERROR, "更新用户信息失败");
        }
    }

    /**
     * 获取对象中值为null的属性名数组
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }



    @Override
    public String uploadAvatar(MultipartFile file) {
        return uploadUtils.upload(file, "avatar");
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        } else if (principal instanceof String) {
            try {
                return Long.parseLong((String) principal);
            } catch (NumberFormatException e) {
                throw new BusinessException(ResultCode.UNAUTHORIZED);
            }
        }
        throw new BusinessException(ResultCode.UNAUTHORIZED);
    }
} 