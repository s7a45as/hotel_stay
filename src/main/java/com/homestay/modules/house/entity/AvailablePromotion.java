package com.homestay.modules.house.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "可用的优惠活动")
public class AvailablePromotion {
    
    @Schema(description = "活动ID")
    private Long id;
    
    @Schema(description = "活动名称")
    private String name;
    
    @Schema(description = "活动类型")
    private String type;
    
    @Schema(description = "优惠力度(折扣类型时为折扣率，满减类型时为优惠金额)")
    private BigDecimal discount;
    
    @Schema(description = "使用门槛(满减类型时的最低消费)")
    private BigDecimal threshold;
    
    @Schema(description = "活动开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "活动结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "活动描述")
    private String description;
    
    @Schema(description = "预计优惠金额")
    private BigDecimal estimatedDiscount;
} 