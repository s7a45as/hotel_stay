package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.modules.home.entity.Promotion;
import com.homestay.modules.house.mapper.SystemPromotionMapper;
import com.homestay.modules.house.service.SystemPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemPromotionServiceImpl implements SystemPromotionService {

    private final SystemPromotionMapper systemPromotionMapper;

    @Override
    public List<Promotion> getValidPromotions(LocalDateTime startTime, LocalDateTime endTime) {
        // 构建查询条件
        LambdaQueryWrapper<Promotion> queryWrapper = new LambdaQueryWrapper<Promotion>()
            .eq(Promotion::getStatus, 1) // 活动状态为进行中
            .le(Promotion::getStartTime, endTime) // 活动开始时间早于预订结束时间
            .ge(Promotion::getEndTime, startTime) // 活动结束时间晚于预订开始时间
            .orderByDesc(Promotion::getValue); // 按优惠值降序排序

        return systemPromotionMapper.selectList(queryWrapper);
    }
} 