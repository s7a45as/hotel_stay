package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.TrendDataDTO;
import com.homestay.modules.merchant.service.MerchantDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商家后台-仪表盘", description = "商家仪表盘相关接口")
@RestController
@RequestMapping("/api/merchant/dashboard")
@RequiredArgsConstructor
public class MerchantDashboardController {

    private final MerchantDashboardService dashboardService;

    @Operation(summary = "获取统计数据")
    @GetMapping("/statistics")
    public Result<DashboardStatisticsDTO> getStatistics() {
        return Result.success(dashboardService.getStatistics());
    }

    @Operation(summary = "获取订单趋势")
    @GetMapping("/order-trend")
    public Result<TrendDataDTO> getOrderTrend() {
        return Result.success(dashboardService.getOrderTrend());
    }

    @Operation(summary = "获取收入趋势")
    @GetMapping("/income-trend")
    public Result<TrendDataDTO> getIncomeTrend() {
        return Result.success(dashboardService.getIncomeTrend());
    }
} 