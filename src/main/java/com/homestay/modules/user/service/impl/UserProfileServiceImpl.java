package com.homestay.modules.user.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.user.dto.UserProfileDTO;
import com.homestay.modules.user.entity.UserInfo;
import com.homestay.modules.user.mapper.UserMapper;
import com.homestay.modules.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void updateUserProfile(UserProfileDTO userProfileDTO) {
        Long userId = getCurrentUserId();
        UserInfo user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        
        BeanUtils.copyProperties(userProfileDTO, user);
        userMapper.updateById(user);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        Long userId = getCurrentUserId();
        UserInfo user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.OLD_PASSWORD_ERROR);
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateById(user);
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