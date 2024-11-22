package com.homestay.modules.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付二维码返回对象")
public class PaymentQRCodeVo {
    
    @Schema(description = "Base64编码的收款码图片数据")
    private String qrCodeBase64;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "支付金额")
    private BigDecimal amount;
    
    @Schema(description = "支付方式")
    private String paymentMethod;
} 