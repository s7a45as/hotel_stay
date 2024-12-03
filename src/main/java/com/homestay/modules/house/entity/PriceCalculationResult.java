package com.homestay.modules.house.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "价格计算结果")
public class PriceCalculationResult {
    
    @Schema(description = "原始价格")
    private BigDecimal originalPrice;
    
    @Schema(description = "最终价格")
    private BigDecimal finalPrice;
    
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;
    
    @Schema(description = "应用的优惠活动")
    private List<AppliedPromotion> appliedPromotions;
    
    @Schema(description = "可用的优惠活动")
    private List<AvailablePromotion> availablePromotions;
} 