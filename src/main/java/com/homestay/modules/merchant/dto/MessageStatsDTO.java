package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "消息统计数据")
public class MessageStatsDTO {

    @Schema(description = "总消息数")
    private Long total;

    @Schema(description = "未读消息数")
    private Long unread;

    @Schema(description = "系统通知数")
    private Long system;

    @Schema(description = "订单消息数")
    private Long order;

    @Schema(description = "用户评价数")
    private Long comment;

    @Schema(description = "安全提醒数")
    private Long security;
} 