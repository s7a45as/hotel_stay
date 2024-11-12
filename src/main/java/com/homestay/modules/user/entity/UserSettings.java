package com.homestay.modules.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_settings")
@Schema(description = "用户设置")
public class UserSettings {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "邮件通知")
    private Boolean emailNotification;
    
    @Schema(description = "短信通知")
    private Boolean smsNotification;
    
    @Schema(description = "订单更新通知")
    private Boolean orderUpdateNotification;
    
    @Schema(description = "促销通知")
    private Boolean promotionNotification;
    
    @Schema(description = "语言")
    private String language;
    
    @Schema(description = "货币")
    private String currency;
    
    @Schema(description = "时区")
    private String timezone;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 