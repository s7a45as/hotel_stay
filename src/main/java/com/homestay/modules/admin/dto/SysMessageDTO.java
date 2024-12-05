package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "系统消息DTO")
public class SysMessageDTO {
    
    @NotBlank(message = "消息标题不能为空")
    @Schema(description = "消息标题")
    private String title;
    
    @NotBlank(message = "消息内容不能为空")
    @Schema(description = "消息内容")
    private String content;
    
    @NotBlank(message = "消息类型不能为空")
    @Schema(description = "消息类型(SYSTEM/PROMOTION/SECURITY)")
    private String type;
    
    @NotBlank(message = "发送对象不能为空")
    @Schema(description = "发送对象(ALL/MERCHANT/USER)")
    private String targetType;
    
    @NotBlank(message = "优先级不能为空")
    @Schema(description = "优先级(HIGH/NORMAL/LOW)")
    private String priority;
} 