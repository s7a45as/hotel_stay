package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.MerchantRecentActivitiesDTO;
import com.homestay.modules.merchant.dto.TrendIncomeDataDTO;
import com.homestay.modules.merchant.dto.TrendOrderDataDTO;
import com.homestay.modules.merchant.entity.*;
import com.homestay.modules.merchant.mapper.*;
import com.homestay.modules.merchant.service.MerchantDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantDashboardServiceImpl implements MerchantDashboardService {



    private final MerchantHouseMapper merchantHouseMapper;

    private final MerchantPromotionMapper promotionMapper;
    private final MerchantOrderMapper merchantOrderMapper;

    private final SecurityUtil securityUtil;

    private final MerchantIncomeMapper merchantIncomeMapper;

    private final  MerchantTrendDataMapper merchantTrendDataMapper;
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
    public TrendOrderDataDTO getOrderTrend() {
        // 1. 获取当前商家ID
        Long merchantId = securityUtil.getCurrentUserId();

        // 2. 使用 LambdaQueryWrapper 构建查询条件，查找符合商家ID的订单趋势数据
        LambdaQueryWrapper<MerchantTrendData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MerchantTrendData::getMerchantId, merchantId);

        // 3. 从数据库获取该商家的订单趋势数据
        List<MerchantTrendData> trendDataList = merchantTrendDataMapper.selectList(queryWrapper);

        // 4. 提取年份、月份和订单数据
        List<String> years = trendDataList.stream()
                .map(MerchantTrendData::getYears)
                .distinct()
                .collect(Collectors.toList());

        List<String> months = trendDataList.stream()
                .map(MerchantTrendData::getMonths)
                .distinct()
                .collect(Collectors.toList());

        List<Long> orderData = trendDataList.stream()
                .map(MerchantTrendData::getAllOrder)
                .collect(Collectors.toList());

        // 5. 封装为 TrendOrderDataDTO
        TrendOrderDataDTO trendOrderDataDTO = new TrendOrderDataDTO();
        trendOrderDataDTO.setYears(years);
        trendOrderDataDTO.setMonths(months);
        trendOrderDataDTO.setData(orderData);

        // 6. 返回封装好的 TrendOrderDataDTO
        return trendOrderDataDTO;
    }

    @Override
    public TrendIncomeDataDTO getIncomeTrend() {
        // 1. 获取当前商家ID
        Long merchantId = securityUtil.getCurrentUserId();

        // 2. 使用 LambdaQueryWrapper 构建查询条件，查找符合商家ID的收入趋势数据
        LambdaQueryWrapper<MerchantTrendData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MerchantTrendData::getMerchantId, merchantId);

        // 3. 从数据库获取该商家的收入趋势数据
        List<MerchantTrendData> trendDataList = merchantTrendDataMapper.selectList(queryWrapper);

        // 4. 提取年份、月份和收入数据
        List<String> years = trendDataList.stream()
                .map(MerchantTrendData::getYears)
                .distinct()
                .collect(Collectors.toList());

        List<String> months = trendDataList.stream()
                .map(MerchantTrendData::getMonths)
                .distinct()
                .collect(Collectors.toList());

        List<BigDecimal> incomeData = trendDataList.stream()
                .map(MerchantTrendData::getAllPrice)
                .collect(Collectors.toList());

        // 5. 封装为 TrendIncomeDataDTO
        TrendIncomeDataDTO trendIncomeDataDTO = new TrendIncomeDataDTO();
        trendIncomeDataDTO.setYears(years);
        trendIncomeDataDTO.setMonths(months);
        trendIncomeDataDTO.setData(incomeData);

        // 6. 返回封装好的 TrendIncomeDataDTO
        return trendIncomeDataDTO;
    }



    @Override
    public List<MerchantRecentActivitiesDTO> getPromotionList() {
        List<MerchantPromotion> merchantPromotion = promotionMapper.selectList(
                new LambdaQueryWrapper<MerchantPromotion>()
                        .eq(MerchantPromotion::getMerchantId, SecurityUtils.getCurrentUserId())
                        .eq(MerchantPromotion::getStatus, '0')//0---进行中  1---已结束  2---未开始
                        .orderByDesc(MerchantPromotion::getCreateTime)
                        .last("limit 5")
        );

        List<MerchantRecentActivitiesDTO> merchantRecentActivitiesDTO = new ArrayList<>();

        // 使用 BeanUtils 将 MerchantPromotion 转换为 MerchantRecentActivitiesDTO
        for (MerchantPromotion promotion : merchantPromotion) {
            MerchantRecentActivitiesDTO dto = new MerchantRecentActivitiesDTO();
            BeanUtils.copyProperties(promotion, dto);  // 将所有属性拷贝到 DTO 对象
            merchantRecentActivitiesDTO.add(dto);
        }

        return merchantRecentActivitiesDTO;
    }

    /**
     * 获取最近6个月的月份列表
     */
    private List<String> getLast6Months() {
        List<String> months = new ArrayList<>();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");
        
        for (int i = 12; i >= 0; i--) {
            LocalDate month = date.minusMonths(i);
            months.add(month.format(formatter));
        }
        
        return months;
    }
} 