package com.homestay.modules.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_user_message")
@Schema(description = "用户消息实体")
public class UserMessage {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "消息ID")
    private Long id;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "消息标题")
    private String title;
    
    @Schema(description = "消息内容")
    private String content;
    
    @Schema(description = "消息类型(SYSTEM/PROMOTION/SECURITY)")
    private String type;
    
    @Schema(description = "接收对象类型(ALL/MERCHANT/USER)")
    private String targetType;
    
    @Schema(description = "优先级(HIGH/NORMAL/LOW)")
    private String priority;
    
    @Schema(description = "发送者")
    private String sender;
    
    @Schema(description = "是否已读(0-未读/1-已读)")
    private Boolean isRead;
    
    @Schema(description = "阅读时间")
    private LocalDateTime readTime;
    
    @Schema(description = "关联的系统消息ID")
    private Long sysMessageId;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @TableLogic
    @Schema(description = "是否删除")
    private Boolean deleted;
} 