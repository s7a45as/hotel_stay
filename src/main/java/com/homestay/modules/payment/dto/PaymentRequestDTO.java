package com.homestay.modules.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "支付请求参数")
public class PaymentRequestDTO {
    
    @NotBlank(message = "订单ID不能为空")
    @Schema(description = "订单ID")
    private String orderId;
    
    @NotBlank(message = "支付方式不能为空")
    @Schema(description = "支付方式：WECHAT-微信支付，ALIPAY-支付宝支付")
    private String paymentMethod;
} 