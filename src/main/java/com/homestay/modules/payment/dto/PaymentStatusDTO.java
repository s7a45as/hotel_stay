package com.homestay.modules.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "支付状态信息")
public class PaymentStatusDTO {
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "支付状态")
    private Integer status;
} 