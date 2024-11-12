package com.homestay.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户设置DTO")
public class UserSettingsDTO {
    
    @Schema(description = "通知设置")
    private NotificationSettings notifications;
    
    @Schema(description = "偏好设置")
    private PreferenceSettings preferences;
    
    @Data
    public static class NotificationSettings {
        @Schema(description = "邮件通知")
        private Boolean email;
        
        @Schema(description = "短信通知")
        private Boolean sms;
        
        @Schema(description = "订单更新通知")
        private Boolean orderUpdates;
        
        @Schema(description = "促销通知")
        private Boolean promotions;
    }
    
    @Data
    public static class PreferenceSettings {
        @Schema(description = "语言")
        private String language;
        
        @Schema(description = "货币")
        private String currency;
        
        @Schema(description = "时区")
        private String timezone;
    }
} 