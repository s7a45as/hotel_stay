package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.DashboardStatisticsDTO;
import com.homestay.modules.admin.dto.TrendDataDTO;
import com.homestay.modules.admin.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台-仪表盘", description = "提供管理后台首页的统计数据和趋势分析，需要管理员权限")
@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @Operation(summary = "获取统计数据", 
              description = "获取平台的核心统计数据，包括总用户数、总订单数、总房源数和总收入。\n" +
                          "此接口每5分钟缓存一次数据。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功", 
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DashboardStatisticsDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/statistics")
    public Result<DashboardStatisticsDTO> getStatistics() {
        return Result.success(adminDashboardService.getStatistics());
    }

    @Operation(summary = "获取用户趋势", 
              description = "获取最近6个月的用户增长趋势数据。\n" +
                          "数据每天凌晨3点更新一次。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功", 
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrendDataDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/userTrend")
    public Result<TrendDataDTO> getUserTrend() {
        return Result.success(adminDashboardService.getUserTrend());
    }

    @Operation(summary = "获取订单趋势", 
              description = "获取最近6个月的订单量趋势数据。\n" +
                          "数据每天凌晨3点更新一次。")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功", 
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrendDataDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/orderTrend")
    public Result<TrendDataDTO> getOrderTrend() {
        return Result.success(adminDashboardService.getOrderTrend());
    }
} 