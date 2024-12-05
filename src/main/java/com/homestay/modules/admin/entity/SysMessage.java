package com.homestay.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_message")
@Schema(description = "系统消息")
public class SysMessage {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "消息ID")
    private Long id;
    
    @Schema(description = "消息标题")
    private String title;
    
    @Schema(description = "消息内容")
    private String content;
    
    @Schema(description = "消息类型(SYSTEM-系统通知/PROMOTION-活动通知/SECURITY-安全提醒)")
    private String type;
    
    @Schema(description = "发送对象(ALL-所有用户/MERCHANT-商家/USER-普通用户)")
    private String targetType;
    
    @Schema(description = "优先级(HIGH-高/NORMAL-普通/LOW-低)")
    private String priority;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "创建者")
    private String createBy;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @Schema(description = "更新者")
    private String updateBy;
    
    @TableLogic
    @Schema(description = "是否删除")
    private Integer deleted;
} 