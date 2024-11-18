package com.homestay.modules.order.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.order.dto.OrderCreateDTO;
import com.homestay.modules.order.dto.OrderDetailDTO;
import com.homestay.modules.order.service.UserOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户-订单管理", description = "用户订单相关接口")
@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;

    @Operation(summary = "创建订单")
    @PostMapping("/create")
    public Result<?> createOrder(@Valid @RequestBody OrderCreateDTO createDTO) {
        return Result.success(userOrderService.createOrder(createDTO));
    }

    @Operation(summary = "获取订单详情")
    @GetMapping("/{orderId}")
    public Result<OrderDetailDTO> getOrderDetail(@PathVariable String orderId) {
        return Result.success(userOrderService.getOrderDetail(orderId));
    }

    @Operation(summary = "获取订单列表")
    @GetMapping("/list")
    public Result<?> getOrderList(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(userOrderService.getOrderList(currentPage, pageSize, status));
    }

    @Operation(summary = "取消订单")
    @PostMapping("/{orderId}/cancel")
    public Result<?> cancelOrder(@PathVariable String orderId) {
        userOrderService.cancelOrder(orderId);
        return Result.success();
    }

    @Operation(summary = "申请退款")
    @PostMapping("/{orderId}/refund")
    public Result<?> applyRefund(@PathVariable String orderId) {
        userOrderService.applyRefund(orderId);
        return Result.success();
    }
} 