package com.homestay.modules.user.service;

import com.homestay.modules.user.dto.UserSettingsDTO;

public interface UserSettingsService {
    
    /**
     * 获取用户设置
     */
    UserSettingsDTO getUserSettings();
    
    /**
     * 更新用户设置
     */
    void updateUserSettings(UserSettingsDTO settingsDTO);
} 