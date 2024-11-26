package com.homestay.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_merchant_message")
@Schema(description = "商家消息")
public class MerchantMessage {

    @TableId(type = IdType.AUTO)
    @Schema(description = "消息ID")
    private Long id;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description = "消息标题")
    private String title;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "消息类型(system:系统通知 order:订单消息 comment:用户评价 security:安全提醒)")
    private String type;

    @Schema(description = "优先级(normal:普通 high:重要)")
    private String priority;

    @Schema(description = "发送者")
    private String sender;

    @Schema(description = "是否已读")
    private Boolean isRead;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除")
    @TableLogic
    private Boolean deleted;
} 