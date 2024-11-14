package com.homestay.modules.admin.service.impl;

import com.homestay.modules.admin.dto.DashboardStatisticsDTO;
import com.homestay.modules.admin.dto.TrendDataDTO;
import com.homestay.modules.admin.entity.TrendData;
import com.homestay.modules.admin.mapper.AdminHouseMapper;
import com.homestay.modules.admin.mapper.AdminOrderMapper;
import com.homestay.modules.admin.mapper.TrendDataMapper;
import com.homestay.modules.admin.service.AdminDashboardService;
import com.homestay.modules.auth.mapper.NormalUserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final AdminOrderMapper adminOrderMapper;
    private final AdminHouseMapper houseMapper;
    private final NormalUserMapper normalUserMapper;
    private final TrendDataMapper trendDataMapper;

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
        // 获取最近6个月的趋势数据
        List<Map<String, Object>> trendDataList = trendDataMapper.getUserTrendData();
        
        TrendDataDTO trendData = new TrendDataDTO();
        List<String> months = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        
        // 处理查询结果
        for (Map<String, Object> item : trendDataList) {
            months.add((String) item.get("month"));
            data.add(((Number) item.get("count")).intValue());
        }
        
        trendData.setMonths(months);
        trendData.setData(data);
        
        return trendData;
    }

    @Override
    public TrendDataDTO getOrderTrend() {
        // 获取最近6个月的订单趋势数据
        List<Map<String, Object>> trendDataList = trendDataMapper.getOrderTrendData();
        
        TrendDataDTO trendData = new TrendDataDTO();
        List<String> months = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        
        // 处理查询结果
        for (Map<String, Object> item : trendDataList) {
            months.add((String) item.get("month"));
            data.add(((Number) item.get("count")).intValue());
        }
        
        trendData.setMonths(months);
        trendData.setData(data);
        
        return trendData;
    }

    /**
     * 记录用户趋势数据
     * 建议通过定时任务每天凌晨执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void recordUserTrendData() {
        // 统计当天的用户数
        long userCount = normalUserMapper.selectCount(null);
        
        // 记录趋势数据
        TrendData trendData = new TrendData();
        trendData.setType("USER");
        trendData.setDate(LocalDate.now());
        trendData.setCount((int) userCount);
        
        trendDataMapper.insert(trendData);


    }

    /**
     * 记录订单趋势数据
     * 每天凌晨执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void recordOrderTrendData() {
        try {
            // 统计当天的订单数
            Integer orderCount = adminOrderMapper.countTotalOrders();
            
            // 记录趋势数据
            TrendData trendData = new TrendData();
            trendData.setType("ORDER");
            trendData.setDate(LocalDate.now());
            trendData.setCount(orderCount);
            
            trendDataMapper.insert(trendData);
            
            log.info("记录订单趋势数据完成: {}", trendData);
        } catch (Exception e) {
            log.error("记录订单趋势数据失败", e);
        }
    }
} 