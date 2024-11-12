package com.homestay.modules.merchant.service.impl;

import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.TrendDataDTO;
import com.homestay.modules.merchant.service.MerchantDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantDashboardServiceImpl implements MerchantDashboardService {

    @Override
    public DashboardStatisticsDTO getStatistics() {
        // TODO: 实现从数据库获取实际统计数据
        DashboardStatisticsDTO statistics = new DashboardStatisticsDTO();
        statistics.setTotalHouses(15);
        statistics.setTotalOrders(150);
        statistics.setTotalIncome(new BigDecimal("50000"));
        statistics.setPendingOrders(5);
        return statistics;
    }

    @Override
    public TrendDataDTO getOrderTrend() {
        // TODO: 实现从数据库获取实际订单趋势数据
        TrendDataDTO trend = new TrendDataDTO();
        trend.setMonths(getLast6Months());
        trend.setData(List.of(25, 32, 28, 36, 30, 42));
        return trend;
    }

    @Override
    public TrendDataDTO getIncomeTrend() {
        // TODO: 实现从数据库获取实际收入趋势数据
        TrendDataDTO trend = new TrendDataDTO();
        trend.setMonths(getLast6Months());
        trend.setData(List.of(5000, 6500, 5800, 7200, 6000, 8500));
        return trend;
    }

    /**
     * 获取最近6个月的月份列表
     */
    private List<String> getLast6Months() {
        List<String> months = new ArrayList<>();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");
        
        for (int i = 5; i >= 0; i--) {
            LocalDate month = date.minusMonths(i);
            months.add(month.format(formatter));
        }
        
        return months;
    }
} 