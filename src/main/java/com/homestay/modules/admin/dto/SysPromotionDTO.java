package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "优惠活动DTO")
public class SysPromotionDTO {
    
    @Schema(description = "活动标题")
    @NotBlank(message = "活动标题不能为空")
    private String title;
    
    @Schema(description = "活动描述")
    private String description;
    
    @Schema(description = "活动类型：DISCOUNT-折扣, COUPON-优惠券")
    @NotBlank(message = "活动类型不能为空")
    private String type;
    
    @Schema(description = "优惠金额/折扣率")
    @NotNull(message = "优惠值不能为空")
    private BigDecimal value;
    
    @Schema(description = "活动开始时间")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;
    
    @Schema(description = "活动结束时间")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
    
    @Schema(description = "活动图片URL")
    private String image;
} 