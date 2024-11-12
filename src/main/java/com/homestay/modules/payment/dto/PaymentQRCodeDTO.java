package com.homestay.modules.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "支付二维码信息")
public class PaymentQRCodeDTO {
    
    @Schema(description = "二维码URL")
    private String qrCodeUrl;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "支付金额")
    private BigDecimal amount;
    
    @Schema(description = "过期时间")
    private Long expireTime;
} 