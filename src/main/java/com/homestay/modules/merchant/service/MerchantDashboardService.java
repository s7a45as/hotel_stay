package com.homestay.modules.merchant.service;

import com.homestay.modules.merchant.dto.DashboardStatisticsDTO;
import com.homestay.modules.merchant.dto.MerchantRecentActivitiesDTO;
import com.homestay.modules.merchant.dto.TrendIncomeDataDTO;
import com.homestay.modules.merchant.dto.TrendOrderDataDTO;

import java.util.List;

public interface MerchantDashboardService {
    
    /**
     * 获取商家统计数据
     */
    DashboardStatisticsDTO getStatistics();
    
    /**
     * 获取订单趋势
     */
    TrendOrderDataDTO getOrderTrend();

    /**
     * 获取收入趋势
     */
    TrendIncomeDataDTO getIncomeTrend();




    /*
    * 获取最近的活动
    * 1.要在进行的活动
    * 2.限条数为10条
    * */
    List<MerchantRecentActivitiesDTO> getPromotionList();
} 