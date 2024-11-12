package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.OrderPageDTO;
import com.homestay.modules.merchant.service.MerchantOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商家后台-订单管理", description = "商家订单管理相关接口")
@RestController
@RequestMapping("/api/merchant/orders")
@RequiredArgsConstructor
public class MerchantOrderController {

    private final MerchantOrderService orderService;

    @Operation(summary = "获取订单列表")
    @GetMapping
    public Result<OrderPageDTO> getOrderList(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String status
    ) {
        return Result.success(orderService.getOrderList(currentPage, pageSize, orderId, userName, status));
    }

    @Operation(summary = "处理退款申请")
    @PostMapping("/{orderId}/refund")
    public Result<Void> handleRefund(
            @PathVariable String orderId,
            @RequestParam Boolean agree
    ) {
        orderService.handleRefund(orderId, agree);
        return Result.success();
    }

    @Operation(summary = "更新订单状态")
    @PutMapping("/{orderId}/status")
    public Result<Void> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam Integer status
    ) {
        orderService.updateOrderStatus(orderId, status);
        return Result.success();
    }
} 