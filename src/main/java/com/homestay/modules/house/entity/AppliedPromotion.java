package com.homestay.modules.house.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "已应用的优惠活动")
public class AppliedPromotion {
    
    @Schema(description = "活动ID")
    private Long id;
    
    @Schema(description = "活动名称")
    private String name;
    
    @Schema(description = "活动类型")
    private String type;
    
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;
} 