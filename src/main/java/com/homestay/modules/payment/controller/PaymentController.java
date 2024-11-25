package com.homestay.modules.payment.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.payment.vo.PaymentQRCodeVo;
import com.homestay.modules.payment.dto.PaymentRequestDTO;
import com.homestay.modules.payment.dto.PaymentStatusDTO;
import com.homestay.modules.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "支付管理", description = "提供订单支付相关功能，包括生成支付二维码、查询支付状态等")
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "生成支付二维码", 
              description = "根据订单ID和支付方式生成对应的支付二维码，支持微信和支付宝")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "生成成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PaymentQRCodeVo.class))),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "订单不存在"),
        @ApiResponse(responseCode = "409", description = "订单状态不正确")
    })
    @PostMapping("/qrcode")
    public Result<PaymentQRCodeVo> generatePayQRCode(
        @Parameter(description = "支付请求参数，包含订单ID和支付方式", required = true)
        @Valid @RequestBody PaymentRequestDTO request
    ) {
        return Result.success(paymentService.generatePayQRCode(request));
    }

    @Operation(summary = "查询支付状态", 
              description = "查询指定订单的支付状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PaymentStatusDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @GetMapping("/status/{orderId}")
    public Result<PaymentStatusDTO> getPaymentStatus(
        @Parameter(description = "订单ID", required = true, 
                  example = "ORDER202403150001")
        @PathVariable String orderId
    ) {
        return Result.success(paymentService.getPaymentStatus(orderId));
    }

    @Operation(summary = "支付回调接口", 
              description = "处理支付平台的支付结果回调，更新订单支付状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "处理成功"),
        @ApiResponse(responseCode = "400", description = "无效的支付状态"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @PostMapping("/callback/{orderId}")
    public Result<Void> handlePaymentCallback(
            @Parameter(description = "订单ID", required = true, 
                      example = "ORDER202403150001") 
            @PathVariable String orderId,
            
            @Parameter(description = "支付状态：SUCCESS-支付成功，FAIL-支付失败", 
                      required = true, example = "SUCCESS") 
            @RequestParam String status
    ) {
        paymentService.handlePaymentCallback(orderId, status);
        return Result.success();
    }
} 