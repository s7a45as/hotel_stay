package com.homestay.modules.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_user_order")
@Schema(description = "订单实体")
public class UserOrder {
    
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "订单ID")
    private String id;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "房源ID")
    private Long houseId;
    
    @Schema(description = "订单金额")
    private BigDecimal amount;
    
    @Schema(description = "入住日期")
    private LocalDate checkIn;
    
    @Schema(description = "退房日期")
    private LocalDate checkOut;
    
    @Schema(description = "入住天数")
    private Integer nights;
    
    @Schema(description = "入住人数")
    private Integer guests;
    
    @Schema(description = "联系人姓名")
    private String contactName;
    
    @Schema(description = "联系人电话")
    private String contactPhone;
    
    @Schema(description = "特殊要求")
    private String specialRequests;
    
    @Schema(description = "订单状态：0-待支付，1-已支付，2-已取消，3-已完成")
    private Integer status;
    
    @Schema(description = "支付时间")
    private LocalDateTime payTime;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


}