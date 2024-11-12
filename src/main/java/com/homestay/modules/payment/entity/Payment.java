package com.homestay.modules.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_payment")
@Schema(description = "支付信息")
public class Payment {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "支付ID")
    private Long id;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "支付金额")
    private BigDecimal amount;
    
    @Schema(description = "支付方式")
    private String method;
    
    @Schema(description = "支付状态(PENDING-待支付,PAID-已支付,CANCELLED-已取消)")
    private String status;
    
    @Schema(description = "支付二维码")
    private String qrcode;
    
    @Schema(description = "支付时间")
    private LocalDateTime payTime;
    
    @Schema(description = "二维码过期时间")
    private LocalDateTime expireTime;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 