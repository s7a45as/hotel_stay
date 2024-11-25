package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.OrderPageDTO;
import com.homestay.modules.admin.service.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "管理后台-订单管理", description = "提供订单的查询、状态更新、删除和导出等功能，需要管理员权限")
@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @Operation(summary = "获取订单列表", 
              description = "分页获取订单列表，支持按订单号、用户名和状态筛选")
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
            
            @Parameter(description = "订单状态：PENDING-待支付，PAID-已支付，COMPLETED-已完成等") 
            @RequestParam(required = false) String status
    ) {
        return Result.success(adminOrderService.getOrderList(currentPage, pageSize, orderId, userName, status));
    }

    @Operation(summary = "更新订单状态", description = "更新指定订单的状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "状态值无效"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @PutMapping("/{id}/status")
    public Result<Void> updateOrderStatus(
            @Parameter(description = "订单ID", required = true, example = "ORDER202403150001") 
            @PathVariable String id,
            
            @Parameter(description = "新状态：PENDING-待支付，PAID-已支付，COMPLETED-已完成等", 
                      required = true, example = "COMPLETED") 
            @RequestParam String status
    ) {
        adminOrderService.updateOrderStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "删除订单", description = "删除指定的订单（软删除）")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "删除成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "订单不存在")
    })
    @DeleteMapping("/{id}")
    public Result<Void> deleteOrder(
        @Parameter(description = "订单ID", required = true, example = "ORDER202403150001") 
        @PathVariable String id
    ) {
        adminOrderService.deleteOrder(id);
        return Result.success();
    }

    @Operation(summary = "导出订单数据", 
              description = "导出订单数据为Excel文件，支持筛选条件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "导出成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportOrders(
            @Parameter(description = "订单号", example = "ORDER202403150001") 
            @RequestParam(required = false) String orderId,
            
            @Parameter(description = "用户名", example = "张三") 
            @RequestParam(required = false) String userName,
            
            @Parameter(description = "订单状态：PENDING-待支付，PAID-已支付，COMPLETED-已完成等") 
            @RequestParam(required = false) String status
    ) {
        byte[] data = adminOrderService.exportOrders(orderId, userName, status);
        String filename = String.format("订单数据_%s.xls", 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", 
            new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
} 