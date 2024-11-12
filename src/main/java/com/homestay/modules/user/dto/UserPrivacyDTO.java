package com.homestay.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户隐私设置DTO")
public class UserPrivacyDTO {
    
    @Schema(description = "个人资料可见性")
    private String profileVisibility;
    
    @Schema(description = "显示在线状态")
    private Boolean showOnlineStatus;
    
    @Schema(description = "显示最后在线时间")
    private Boolean showLastSeen;
    
    @Schema(description = "允许好友请求")
    private Boolean allowFriendRequests;
    
    @Schema(description = "允许消息")
    private Boolean allowMessages;
    
    @Schema(description = "显示邮箱")
    private Boolean showEmail;
    
    @Schema(description = "显示手机号")
    private Boolean showPhone;
} 