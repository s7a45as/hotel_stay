package com.homestay.modules.payment.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.payment.vo.PaymentQRCodeVo;
import com.homestay.modules.payment.dto.PaymentRequestDTO;
import com.homestay.modules.payment.dto.PaymentStatusDTO;
import com.homestay.modules.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "支付管理", description = "支付相关接口")
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "生成支付二维码")
    @PostMapping("/qrcode")
    public Result<PaymentQRCodeVo> generatePayQRCode(@Valid @RequestBody PaymentRequestDTO request) {
        return Result.success(paymentService.generatePayQRCode(request));
    }

    @Operation(summary = "查询支付状态")
    @GetMapping("/status/{orderId}")
    public Result<PaymentStatusDTO> getPaymentStatus(@PathVariable String orderId) {
        return Result.success(paymentService.getPaymentStatus(orderId));
    }

    @Operation(summary = "支付回调接口")
    @PostMapping("/callback/{orderId}")
    public Result<Void> handlePaymentCallback(
            @PathVariable String orderId,
            @RequestParam String status) {
        paymentService.handlePaymentCallback(orderId, status);
        return Result.success();
    }
} 