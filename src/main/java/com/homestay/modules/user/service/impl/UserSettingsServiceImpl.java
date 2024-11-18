package com.homestay.modules.user.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.user.dto.UserSettingsDTO;
import com.homestay.modules.user.entity.UserInfoSettings;
import com.homestay.modules.user.mapper.UserSettingsMapper;
import com.homestay.modules.user.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserSettingsMapper userSettingsMapper;

    @Override
    public UserSettingsDTO getUserSettings() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoSettings settings = userSettingsMapper.selectByUserId(userId);
        if (settings == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        UserSettingsDTO settingsDTO = new UserSettingsDTO();
        
        UserSettingsDTO.NotificationSettings notifications = new UserSettingsDTO.NotificationSettings();
        notifications.setEmail(settings.getEmailNotification());
        notifications.setSms(settings.getSmsNotification());
        notifications.setOrderUpdates(settings.getOrderUpdateNotification());
        notifications.setPromotions(settings.getPromotionNotification());
        settingsDTO.setNotifications(notifications);
        
        UserSettingsDTO.PreferenceSettings preferences = new UserSettingsDTO.PreferenceSettings();
        preferences.setLanguage(settings.getLanguage());
        preferences.setCurrency(settings.getCurrency());
        preferences.setTimezone(settings.getTimezone());
        settingsDTO.setPreferences(preferences);
        
        return settingsDTO;
    }

    @Override
    public void updateUserSettings(UserSettingsDTO settingsDTO) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoSettings settings = userSettingsMapper.selectByUserId(userId);
        if (settings == null) {
            settings = new UserInfoSettings();
            settings.setUserId(userId);
        }
        
        UserSettingsDTO.NotificationSettings notifications = settingsDTO.getNotifications();
        settings.setEmailNotification(notifications.getEmail());
        settings.setSmsNotification(notifications.getSms());
        settings.setOrderUpdateNotification(notifications.getOrderUpdates());
        settings.setPromotionNotification(notifications.getPromotions());
        
        UserSettingsDTO.PreferenceSettings preferences = settingsDTO.getPreferences();
        settings.setLanguage(preferences.getLanguage());
        settings.setCurrency(preferences.getCurrency());
        settings.setTimezone(preferences.getTimezone());
        
        if (settings.getId() == null) {
            userSettingsMapper.insert(settings);
        } else {
            userSettingsMapper.updateById(settings);
        }
    }
} 