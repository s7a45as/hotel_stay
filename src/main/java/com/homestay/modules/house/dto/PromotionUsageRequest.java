package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "优惠使用记录请求")
public class PromotionUsageRequest {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;

    @NotNull(message = "房源ID不能为空")
    @Schema(description = "房源ID")
    private String houseId;

    @NotNull(message = "原始金额不能为空")
    @Schema(description = "原始金额")
    private BigDecimal originalAmount;

    @NotNull(message = "最终金额不能为空")
    @Schema(description = "最终金额")
    private BigDecimal finalAmount;

    @NotEmpty(message = "优惠列表不能为空")
    @Schema(description = "使用的优惠列表")
    private List<PromotionDetail> promotions;

    @Data
    @Schema(description = "优惠详情")
    public static class PromotionDetail {
        @NotNull(message = "优惠ID不能为空")
        @Schema(description = "优惠ID")
        private Long promotionId;

        @NotNull(message = "优惠类型不能为空")
        @Schema(description = "优惠类型(SYSTEM/MERCHANT)")
        private String type;

        @NotNull(message = "优惠活动名称不能为空")
        @Schema(description = "优惠活动名称")
        private String name;

        @NotNull(message = "优惠金额不能为空")
        @Schema(description = "优惠金额")
        private BigDecimal discountAmount;

        @NotNull(message = "使用时间不能为空")
        @Schema(description = "使用时间")
        private String useTime;
    }
} 