package com.homestay.modules.user.service;

import com.homestay.modules.user.dto.UserPrivacyDTO;

public interface UserPrivacyService {
    
    /**
     * 获取隐私设置
     */
    UserPrivacyDTO getPrivacySettings();
    
    /**
     * 更新隐私设置
     */
    void updatePrivacySettings(UserPrivacyDTO privacyDTO);
} 