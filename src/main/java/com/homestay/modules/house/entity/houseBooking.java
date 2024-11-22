package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
@Schema(description = "预订信息")
public class houseBooking {
    
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "预订ID")
    private String id;
    
    @Schema(description = "房源ID")
    private Long houseId;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "入住时间")
    private LocalDateTime checkInTime;
    
    @Schema(description = "退房时间")
    private LocalDateTime checkOutTime;
    
    @Schema(description = "入住人数")
    private Integer guestCount;
    
    @Schema(description = "联系人姓名")
    private String contactName;
    
    @Schema(description = "联系人电话")
    private String contactPhone;
    
    @Schema(description = "特殊要求")
    private String specialRequests;
    
    @Schema(description = "订单总价")
    private BigDecimal totalPrice;
    
    @Schema(description = "订单状态(0:待支付 1:已支付 2:已取消 3:已完成)")
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @Version
    private Integer version;
    
    @TableLogic
    private Integer deleted;
} 