package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.MerchantOrderDetailDTO;
import com.homestay.modules.merchant.dto.OrderPageDTO;
import com.homestay.modules.merchant.dto.UserSearchDTO;
import com.homestay.modules.merchant.service.MerchantOrderService;
import com.homestay.modules.order.dto.OrderDetailDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商家后台-订单管理", description = "提供商家订单的查询和处理功能")
@RestController
@RequestMapping("/merchant/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class MerchantOrderController {

    private final MerchantOrderService orderService;

    @Operation(summary = "获取订单列表", 
              description = "分页获取商家的订单列表，支持多种筛选条件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OrderPageDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping
    public Result<OrderPageDTO> getOrderList(
            @Parameter(description = "当前页码", example = "1")
            @RequestParam(defaultValue = "1") Integer currentPage,
            
            @Parameter(description = "每页数量", example = "10") 
            @RequestParam(defaultValue = "10") Integer pageSize,
            
            @Parameter(description = "订单号", example = "ORDER202403150001") 
            @RequestParam(required = false) String orderId,
            
            @Parameter(description = "用户名", example = "张三") 
            @RequestParam(required = false) String userName,
            
            @Parameter(description = "订单状态：PENDING-待支付，PAID-已支付等") 
            @RequestParam(required = false) String status
    ) {
        return Result.success(orderService.getOrderList(currentPage, pageSize, orderId, userName, status));
    }

    @Operation(summary = "处理退款申请", 
              description = "处理用户的退款申请，可以选择同意或拒绝")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "处理成功"),
        @ApiResponse(responseCode = "400", description = "订单状态不正确"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @PostMapping("/{orderId}/refund")
    public Result<Void> handleRefund(
            @Parameter(description = "订单ID", required = true, 
                      example = "ORDER202403150001") 
            @PathVariable String orderId,
            
            @Parameter(description = "是否同意退款", required = true, example = "true") 
            @RequestParam Boolean agree
    ) {
        orderService.handleRefund(orderId, agree);
        return Result.success();
    }

    @Operation(summary = "更新订单状态", 
              description = "更新指定订单的状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "状态值无效"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @PutMapping("/{orderId}/status")
    public Result<Void> updateOrderStatus(
            @Parameter(description = "订单ID", required = true, 
                      example = "ORDER202403150001") 
            @PathVariable String orderId,
            
            @Parameter(description = "新状态：0-待支付，1-已支付，2-已取消等", 
                      required = true, example = "1") 
            @RequestParam Integer status
    ) {
        orderService.updateOrderStatus(orderId, status);
        return Result.success();
    }

    @GetMapping("/{orderId}/details")
    public Result<MerchantOrderDetailDTO> getOrderDetail(
            @Parameter(description = "订单ID", required = true, 
                      example = "ORDER202403150001") 
            @PathVariable String orderId
    ){
        return Result.success(orderService.getOrderDetail(orderId));
    }

    @Operation(summary = "搜索用户", 
              description = "根据手机号搜索下过订单的用户")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "搜索成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserSearchDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/users/search")
    public Result<List<UserSearchDTO>> searchUsers(
        @Parameter(description = "用户手机号", example = "138") 
        @RequestParam String phone
    ) {
        return Result.success(orderService.searchUsersByPhone(phone));
    }
} 