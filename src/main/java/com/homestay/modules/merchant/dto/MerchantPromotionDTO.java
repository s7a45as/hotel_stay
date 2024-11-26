package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "商家优惠活动DTO")
public class MerchantPromotionDTO {

    @NotBlank(message = "活动名称不能为空")
    @Schema(description = "活动名称")
    private String name;

    @NotBlank(message = "活动类型不能为空")
    @Schema(description = "活动类型：DISCOUNT-折扣，AMOUNT_OFF-满减")
    private String type;

    @NotNull(message = "优惠力度不能为空")
    @Schema(description = "折扣力度")
    private BigDecimal discount;

    @Schema(description = "使用门槛")
    private BigDecimal threshold;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "活动开始时间")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "活动结束时间")
    private LocalDateTime endTime;

    @Schema(description = "活动描述")
    private String description;
} 