package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.dto.SystemSettingsDTO;
import com.homestay.modules.admin.entity.SystemSetting;
import com.homestay.modules.admin.mapper.SystemSettingMapper;
import com.homestay.modules.admin.service.AdminSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminSettingsServiceImpl implements AdminSettingsService {

    private final SystemSettingMapper systemSettingMapper;
    
    @Override
    public SystemSettingsDTO getSettings() {
        SystemSetting setting = systemSettingMapper.selectOne(
            new LambdaQueryWrapper<SystemSetting>()
                .last("LIMIT 1")
        );
        
        if (setting == null) {
            throw new BusinessException("系统设置不存在");
        }
        
        SystemSettingsDTO dto = new SystemSettingsDTO();
        BeanUtils.copyProperties(setting, dto);
        return dto;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSettings(SystemSettingsDTO dto) {
        SystemSetting setting = systemSettingMapper.selectOne(
            new LambdaQueryWrapper<SystemSetting>()
                .last("LIMIT 1")
        );
        
        if (setting == null) {
            setting = new SystemSetting();
        }
        
        BeanUtils.copyProperties(dto, setting);
        
        if (setting.getId() == null) {
            systemSettingMapper.insert(setting);
        } else {
            systemSettingMapper.updateById(setting);
        }
    }
    
    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        
        try {
            // TODO: 实现文件上传逻辑，可以使用云存储服务
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://example.com/uploads/" + fileName;
            return fileUrl;
        } catch (Exception e) {
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }
} 