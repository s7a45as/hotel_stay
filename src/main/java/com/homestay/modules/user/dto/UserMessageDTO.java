package com.homestay.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户消息DTO")
public class UserMessageDTO {
    
    @Schema(description = "消息ID")
    private Long id;
    
    @Schema(description = "消息标题")
    private String title;
    
    @Schema(description = "消息内容")
    private String content;
    
    @Schema(description = "消息类型")
    private String type;
    
    @Schema(description = "优先级")
    private String priority;
    
    @Schema(description = "发送者")
    private String sender;
    
    @Schema(description = "是否已读")
    private Boolean isRead;
    
    @Schema(description = "阅读时间")
    private LocalDateTime readTime;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
} 