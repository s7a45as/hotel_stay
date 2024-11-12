package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.DashboardStatisticsDTO;
import com.homestay.modules.admin.dto.TrendDataDTO;

public interface AdminDashboardService {
    
    DashboardStatisticsDTO getStatistics();
    
    TrendDataDTO getUserTrend();
    
    TrendDataDTO getOrderTrend();
} 