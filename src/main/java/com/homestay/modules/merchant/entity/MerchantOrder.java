package com.homestay.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_all_order")
@Schema(description = "商家订单信息")
public class MerchantOrder {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "订单ID")
    private String id;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description = "房源ID")
    private Long houseId;

    @Schema(description = "房源名称")
    private String houseName;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "入住日期")
    private LocalDate checkIn;

    @Schema(description = "离店日期")
    private LocalDate checkOut;

    @Schema(description = "入住天数")
    private Integer nights;

    @Schema(description = "入住人数")
    private Integer guests;

    @Schema(description = "订单金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @Schema(description = "订单状态(PENDING:待支付 PAID:已支付 CANCELLED:已取消 COMPLETED:已完成 REFUNDING:退款中 REFUNDED:已退款)")
    private String status;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 