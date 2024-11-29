package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.MerchantRecentActivitiesDTO;
import com.homestay.modules.merchant.dto.TrendIncomeDataDTO;
import com.homestay.modules.merchant.dto.TrendOrderDataDTO;
import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.merchant.service.MerchantDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "商家后台-仪表盘", description = "提供商家仪表盘数据，包括统计数据和趋势分析")
@RestController
@RequestMapping("/merchant/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class MerchantDashboardController {

    private final MerchantDashboardService dashboardService;

    @Operation(summary = "获取统计数据", 
              description = "获取商家的核心统计数据，包括总房源数、总订单数、总收入等")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DashboardStatisticsDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/statistics")
    public Result<DashboardStatisticsDTO> getStatistics() {
        return Result.success(dashboardService.getStatistics());
    }

    @Operation(summary = "获取订单趋势", 
              description = "获取最近6个月的订单量趋势数据")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrendIncomeDataDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/order-trend")
    public Result<TrendOrderDataDTO> getOrderTrend() {
        return Result.success(dashboardService.getOrderTrend());
    }

    @Operation(summary = "获取收入趋势", 
              description = "获取最近6个月的收入趋势数据")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TrendIncomeDataDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/income-trend")
    public Result<TrendIncomeDataDTO> getIncomeTrend() {
        return Result.success(dashboardService.getIncomeTrend());
    }

    @Operation(summary = "获取优惠活动列表",
            description = "获取商家的所有优惠活动,用于在控制台展示")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MerchantPromotion.class))),
            @ApiResponse(responseCode = "401", description = "未登录"),
            @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/recent-activities")
    public Result<List<MerchantRecentActivitiesDTO>>getRecentActivities(){

        return Result.success(dashboardService.getPromotionList());

    }
} 