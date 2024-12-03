package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_promotion_usage")
@Schema(description = "优惠活动使用记录")
public class PromotionUsage {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "记录ID")
    private Long id;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "房源ID")
    private String houseId;
    
    @Schema(description = "活动ID")
    private Long promotionId;
    
    @Schema(description = "活动类型")
    private String promotionType;
    
    @Schema(description = "活动名称")
    private String promotionName;
    
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;
    
    @Schema(description = "原始金额")
    private BigDecimal originalAmount;
    
    @Schema(description = "最终金额")
    private BigDecimal finalAmount;
    
    @Schema(description = "使用时间")
    private LocalDateTime useTime;
    
    @Schema(description = "使用状态：1-已使用 2-已失效")
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 