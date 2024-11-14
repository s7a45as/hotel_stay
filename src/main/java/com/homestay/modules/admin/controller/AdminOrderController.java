package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.OrderPageDTO;
import com.homestay.modules.admin.service.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理后台-订单管理", description = "订单管理相关接口")
@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @Operation(summary = "获取订单列表")
    @GetMapping
    public Result<OrderPageDTO> getOrderList(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String status
    ) {
        return Result.success(adminOrderService.getOrderList(currentPage, pageSize, orderId, userName, status));
    }

    @Operation(summary = "更新订单状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateOrderStatus(@PathVariable String id, @RequestParam String status) {
        adminOrderService.updateOrderStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "删除订单")
    @DeleteMapping("/{id}")
    public Result<Void> deleteOrder(@PathVariable String id) {
        adminOrderService.deleteOrder(id);
        return Result.success();
    }
} 