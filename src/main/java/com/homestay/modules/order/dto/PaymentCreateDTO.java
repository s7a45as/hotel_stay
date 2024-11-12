package com.homestay.modules.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "创建支付请求参数")
public class PaymentCreateDTO {
    
    @NotBlank(message = "订单ID不能为空")
    @Schema(description = "订单ID")
    private String orderId;
    
    @NotBlank(message = "支付方式不能为空")
    @Schema(description = "支付方式：ALIPAY-支付宝，WECHAT-微信")
    private String paymentMethod;
} 