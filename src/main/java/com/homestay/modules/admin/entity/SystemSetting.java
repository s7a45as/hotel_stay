package com.homestay.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName(value = "t_system_settings", autoResultMap = true)
public class SystemSetting {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String siteName;
    
    private String siteDesc;
    
    private String logo;
    
    private String favicon;
    
    private String contactPhone;
    
    private String contactEmail;
    
    private String address;
    
    private Integer maxBookingDays;
    
    private Integer minAdvanceBookingDays;
    
    private Integer maxAdvanceBookingDays;
    
    private String checkInTime;
    
    private String checkOutTime;
    
    private Integer depositRate;
    
    private Integer cancelPolicy;
    
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<RefundRule> refundRules;
    
    private Boolean emailNotification;
    
    private Boolean smsNotification;
    
    private Integer notifyBeforeCheckIn;
    
    private Integer notifyBeforeCheckOut;
    
    private Boolean maintenance;
    
    private String maintenanceMessage;
    
    private Boolean registrationEnabled;
    
    private String defaultUserRole;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    @Data
    public static class RefundRule {
        private Integer days;
        private Integer rate;
    }
} 