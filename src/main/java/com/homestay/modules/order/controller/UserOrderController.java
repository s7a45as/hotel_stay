package com.homestay.modules.order.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.order.dto.OrderCreateDTO;
import com.homestay.modules.order.dto.OrderDetailDTO;
import com.homestay.modules.order.service.UserOrderService;
import com.homestay.modules.order.vo.OrderDetailVO;
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

@Tag(name = "用户-订单管理", description = "提供用户订单的创建、查询、取消等功能")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @Operation(summary = "创建订单", 
              description = "创建新的预订订单，创建成功后需要在30分钟内完成支付")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "object", example = "{\"orderId\":\"ORDER202403150001\",\"amount\":299.00}"))),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "409", description = "所选日期已被预订")
    })
    @PostMapping("/create")
    public Result<?> createOrder(
        @Parameter(description = "订单创建信息", required = true)
        @Valid @RequestBody OrderCreateDTO createDTO
    ) {
        return Result.success(userOrderService.createOrder(createDTO));
    }

    @Operation(summary = "获取订单详情", 
              description = "获取指定订单的详细信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OrderDetailVO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权访问此订单"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @GetMapping("/detail/{orderId}")
    public Result<OrderDetailVO> getOrderDetail(
        @Parameter(description = "订单ID", required = true, 
                  example = "ORDER202403150001")
        @PathVariable String orderId
    ) {
        return Result.success(userOrderService.getOrderDetail(orderId));
    }

    @Operation(summary = "获取订单列表", 
              description = "分页获取用户的订单列表，支持按状态筛选")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "object"))),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @GetMapping("/list")
    public Result<?> getOrderList(
            @Parameter(description = "当前页码", example = "1") 
            @RequestParam(defaultValue = "1") Integer currentPage,
            
            @Parameter(description = "每页数量", example = "10") 
            @RequestParam(defaultValue = "10") Integer pageSize,
            
            @Parameter(description = "订单状态：0-待支付，1-已支付，2-已取消，3-已完成", 
                      example = "1") 
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(userOrderService.getOrderList(currentPage, pageSize, status));
    }

    @Operation(summary = "取消订单", 
              description = "取消待支付的订单，已支付的订单需要申请退款")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "取消成功"),
        @ApiResponse(responseCode = "400", description = "订单状态不允许取消"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权操作此订单"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @PostMapping("/{orderId}/cancel")
    public Result<?> cancelOrder(
        @Parameter(description = "订单ID", required = true, 
                  example = "ORDER202403150001")
        @PathVariable String orderId
    ) {
        userOrderService.cancelOrder(orderId);
        return Result.success();
    }

    @Operation(summary = "申请退款", 
              description = "申请订单退款，仅支持已支付的订单申请退款")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "申请成功"),
        @ApiResponse(responseCode = "400", description = "订单状态不允许退款"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权操作此订单"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @PostMapping("/{orderId}/refund")
    public Result<?> applyRefund(
        @Parameter(description = "订单ID", required = true, 
                  example = "ORDER202403150001")
        @PathVariable String orderId
    ) {
        userOrderService.applyRefund(orderId);
        return Result.success();
    }
} 