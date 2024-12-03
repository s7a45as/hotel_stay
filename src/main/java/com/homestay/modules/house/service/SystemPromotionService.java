package com.homestay.modules.house.service;

import com.homestay.modules.home.entity.Promotion;
import java.time.LocalDateTime;
import java.util.List;

public interface SystemPromotionService {
    
    /**
     * 获取有效的系统优惠活动
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 有效的优惠活动列表
     */
    List<Promotion> getValidPromotions(LocalDateTime startTime, LocalDateTime endTime);
} 