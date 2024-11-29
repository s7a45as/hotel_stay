package com.homestay.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_merchant_promotion")
@Schema(description = "商家优惠活动")
public class MerchantPromotion {

    @TableId(type = IdType.AUTO)
    @Schema(description = "活动ID")
    private Long id;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description = "活动名称")
    private String name;

    @Schema(description = "活动类型：DISCOUNT-折扣，AMOUNT_OFF-满减")
    private String type;

    @Schema(description = "折扣力度(折扣类型时为折扣率，满减类型时为优惠金额)")
    private BigDecimal discount;

    @Schema(description = "使用门槛(满减类型时的最低消费)")
    private BigDecimal threshold;

    @Schema(description = "活动开始时间")
    private LocalDateTime startTime;

    @Schema(description = "活动结束时间")
    private LocalDateTime endTime;

    @Schema(description = "活动状态(0:未开始 1:进行中 2:已结束)")
    private Integer status;

    @Schema(description = "活动描述")
    private String description;

    @Schema(description = "目标用户")

    private String targetUsers ;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer deleted;
} 