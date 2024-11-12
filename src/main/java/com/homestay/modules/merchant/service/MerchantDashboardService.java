package com.homestay.modules.merchant.service;

import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.TrendDataDTO;

public interface MerchantDashboardService {
    
    /**
     * 获取商家统计数据
     */
    DashboardStatisticsDTO getStatistics();
    
    /**
     * 获取订单趋势
     */
    TrendDataDTO getOrderTrend();
    
    /**
     * 获取收入趋势
     */
    TrendDataDTO getIncomeTrend();
} 