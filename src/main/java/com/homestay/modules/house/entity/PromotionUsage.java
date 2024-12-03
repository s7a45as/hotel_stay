package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("t_promotion_usage")
@Schema(description = "优惠活动使用记录")
public class PromotionUsage {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String orderId;
    
    private Long promotionId;
    
    private String promotionType; // SYSTEM-系统活动, MERCHANT-商家活动
    
    private String promotionName;
    
    private BigDecimal discountAmount;
    
    private BigDecimal originalAmount;
    
    private BigDecimal finalAmount;
    
    private LocalDateTime useTime;
    
    private Integer status; // 1-已使用 2-已失效
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 