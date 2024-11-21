package com.homestay.modules.user.service;

import com.homestay.modules.user.dto.UserProfileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {
    
    /**
     * 获取用户个人信息
     */
    UserProfileDTO getUserProfile();
    
    /**
     * 更新用户个人信息
     */
    void updateUserProfile(UserProfileDTO userProfileDTO);
    

    /**
     * 上传用户头像
     */
    String uploadAvatar(MultipartFile file);
} 