package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "系统设置DTO")
public class SystemSettingsDTO {
    
    @Schema(description = "网站名称")
    private String siteName;
    
    @Schema(description = "网站描述")
    private String siteDesc;
    
    @Schema(description = "网站logo")
    private String logo;
    
    @Schema(description = "网站图标")
    private String favicon;
    
    @Schema(description = "联系电话")
    private String contactPhone;
    
    @Schema(description = "联系邮箱")
    private String contactEmail;
    
    @Schema(description = "地址")
    private String address;
    
    @Schema(description = "最大预订天数")
    private Integer maxBookingDays;
    
    @Schema(description = "最小提前预订天数")
    private Integer minAdvanceBookingDays;
    
    @Schema(description = "最大提前预订天数")
    private Integer maxAdvanceBookingDays;
    
    @Schema(description = "入住时间")
    private String checkInTime;
    
    @Schema(description = "退房时间")
    private String checkOutTime;
    
    @Schema(description = "定金比例")
    private Integer depositRate;
    
    @Schema(description = "取消政策")
    private Integer cancelPolicy;
    
    @Schema(description = "退款规则")
    private List<RefundRule> refundRules;
    
    @Schema(description = "是否启用邮件通知")
    private Boolean emailNotification;
    
    @Schema(description = "是否启用短信通知")
    private Boolean smsNotification;
    
    @Schema(description = "入住前通知时间(小时)")
    private Integer notifyBeforeCheckIn;
    
    @Schema(description = "退房前通知时间(小时)")
    private Integer notifyBeforeCheckOut;
    
    @Schema(description = "是否维护中")
    private Boolean maintenance;
    
    @Schema(description = "维护信息")
    private String maintenanceMessage;
    
    @Schema(description = "是否开放注册")
    private Boolean registrationEnabled;
    
    @Schema(description = "默认用户角色")
    private String defaultUserRole;
    
    @Data
    public static class RefundRule {
        @Schema(description = "天数")
        private Integer days;
        
        @Schema(description = "退款比例")
        private Integer rate;
    }
} 