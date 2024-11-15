package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.SystemSettingsDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AdminSettingsService {
    
    /**
     * 获取系统设置
     */
    SystemSettingsDTO getSettings();
    
    /**
     * 更新系统设置
     */
    void updateSettings(SystemSettingsDTO settings);
    
    /**
     * 上传文件
     */
    String uploadFile(MultipartFile file);
} 