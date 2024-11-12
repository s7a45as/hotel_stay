package com.homestay.modules.admin.service.impl;

import com.homestay.modules.admin.dto.DashboardStatisticsDTO;
import com.homestay.modules.admin.dto.TrendDataDTO;
import com.homestay.modules.admin.mapper.AdminHouseMapper;
import com.homestay.modules.admin.mapper.AdminOrderMapper;
import com.homestay.modules.admin.service.AdminDashboardService;
import com.homestay.modules.auth.mapper.NormalUserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final AdminOrderMapper adminOrderMapper;
    private final AdminHouseMapper houseMapper;
    private final NormalUserMapper normalUserMapper;

    @Override
    public DashboardStatisticsDTO getStatistics() {
        DashboardStatisticsDTO statistics = new DashboardStatisticsDTO();
        
        // 转换为Integer类型
        statistics.setTotalOrders(adminOrderMapper.countTotalOrders());
        statistics.setTotalHouses(Math.toIntExact(houseMapper.selectCount(null)));
        statistics.setTotalUsers(Math.toIntExact(normalUserMapper.selectCount(null)));
        statistics.setRevenue(adminOrderMapper.sumTotalRevenue());
        
        return statistics;
    }

    @Override
    public TrendDataDTO getUserTrend() {
        TrendDataDTO trendData = new TrendDataDTO();
        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Integer> data = Arrays.asList(456, 789, 654, 876, 543, 987);
        trendData.setMonths(months);
        trendData.setData(data);
        return trendData;
    }

    @Override
    public TrendDataDTO getOrderTrend() {
        TrendDataDTO trendData = new TrendDataDTO();
        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Integer> data = Arrays.asList(45, 78, 56, 89, 65, 92);
        trendData.setMonths(months);
        trendData.setData(data);
        return trendData;
    }
} 