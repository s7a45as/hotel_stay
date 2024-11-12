package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.DashboardStatisticsDTO;
import com.homestay.modules.admin.dto.TrendDataDTO;
import com.homestay.modules.admin.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台-仪表盘", description = "管理后台仪表盘相关接口")
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @Operation(summary = "获取统计数据")
    @GetMapping("/statistics")
    public Result<DashboardStatisticsDTO> getStatistics() {
        return Result.success(adminDashboardService.getStatistics());
    }

    @Operation(summary = "获取用户趋势")
    @GetMapping("/userTrend")
    public Result<TrendDataDTO> getUserTrend() {
        return Result.success(adminDashboardService.getUserTrend());
    }

    @Operation(summary = "获取订单趋势")
    @GetMapping("/orderTrend")
    public Result<TrendDataDTO> getOrderTrend() {
        return Result.success(adminDashboardService.getOrderTrend());
    }
} 