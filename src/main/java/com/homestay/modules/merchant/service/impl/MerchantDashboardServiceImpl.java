package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.TrendDataDTO;
import com.homestay.modules.merchant.entity.MerchantHouse;
import com.homestay.modules.merchant.entity.MerchantIncome;
import com.homestay.modules.merchant.entity.MerchantOrder;
import com.homestay.modules.merchant.mapper.MerchantHouseMapper;
import com.homestay.modules.merchant.mapper.MerchantIncomeMapper;
import com.homestay.modules.merchant.mapper.MerchantMapper;
import com.homestay.modules.merchant.mapper.MerchantOrderMapper;
import com.homestay.modules.merchant.service.MerchantDashboardService;
import com.homestay.modules.merchant.service.MerchantHouseService;
import com.homestay.modules.order.enums.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantDashboardServiceImpl implements MerchantDashboardService {



    private final MerchantHouseMapper merchantHouseMapper;


    private final MerchantOrderMapper merchantOrderMapper;

    private final SecurityUtil securityUtil;

    private final MerchantIncomeMapper merchantIncomeMapper;
    @Override
    public DashboardStatisticsDTO getStatistics() {

        //使用Security获取当前商家用户id
        Long id =securityUtil.getCurrentUserId();


        //去房源表获取对应ID的房源数
        LambdaQueryWrapper<MerchantHouse>wrapper=new LambdaQueryWrapper<MerchantHouse>()
                .eq(MerchantHouse::getMerchantId,id);//筛选条件的配置
        Long HouseTotal =merchantHouseMapper.selectCount(wrapper);//执行筛选条件

        //去订单表获取对应id的订单数
        LambdaQueryWrapper<MerchantOrder>wrapper1=new LambdaQueryWrapper<MerchantOrder>().eq(MerchantOrder::getMerchantId,id);
        Long OrderTotal =merchantOrderMapper.selectCount(wrapper1);
        //去商家收入表获取对应ID的总收入字段
        LambdaQueryWrapper<MerchantIncome>wrapper2=new LambdaQueryWrapper<MerchantIncome>().eq(
                MerchantIncome::getMerchantId,id
        );
        MerchantIncome merchantIncome=merchantIncomeMapper.selectOne(wrapper2);
        BigDecimal AllIncome= merchantIncome.getAll_income();
        //去订单表获取ID 和 PENDING_PAYMENT
        LambdaQueryWrapper<MerchantOrder>wrapper3=new LambdaQueryWrapper<MerchantOrder>()
                .eq(MerchantOrder::getMerchantId,id)
                .eq(MerchantOrder::getStatus, "PENDING_PAYMENT");


        Long PendingOrderTotal =merchantOrderMapper.selectCount(wrapper3);
        DashboardStatisticsDTO statistics = new DashboardStatisticsDTO();
        statistics.setPendingOrders(PendingOrderTotal);
        statistics.setTotalOrders(OrderTotal);
        statistics.setTotalHouses(HouseTotal);
        statistics.setTotalIncome(AllIncome);
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