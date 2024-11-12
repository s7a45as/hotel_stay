package com.homestay.modules.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_order_payment")
@Schema(description = "订单支付实体")
public class OrderPayment {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "支付流水号")
    private String paymentNo;
    
    @Schema(description = "支付方式：ALIPAY-支付宝，WECHAT-微信")
    private String paymentMethod;
    
    @Schema(description = "支付金额")
    private BigDecimal amount;
    
    @Schema(description = "支付状态：0-待支付，1-支付成功，2-支付失败，3-已退款")
    private Integer status;
    
    @Schema(description = "支付时间")
    private LocalDateTime payTime;
    
    @Schema(description = "退款时间")
    private LocalDateTime refundTime;
    
    @Schema(description = "退款金额")
    private BigDecimal refundAmount;
    
    @Schema(description = "退款原因")
    private String refundReason;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 