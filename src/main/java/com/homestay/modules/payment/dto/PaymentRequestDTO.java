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
} 