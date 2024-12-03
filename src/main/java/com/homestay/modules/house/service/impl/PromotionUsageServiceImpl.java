package com.homestay.modules.house.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.modules.house.dto.PromotionUsageRequest;
import com.homestay.modules.house.entity.PromotionUsage;
import com.homestay.modules.house.mapper.PromotionUsageMapper;
import com.homestay.modules.house.service.PromotionUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionUsageServiceImpl implements PromotionUsageService {

    private final PromotionUsageMapper promotionUsageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordUsage(PromotionUsageRequest request, Long userId) {
        try {
            // 验证用户ID是否匹配
            if (!userId.equals(request.getUserId())) {
                throw new BusinessException("无权操作其他用户的数据");
            }

            for (PromotionUsageRequest.PromotionDetail detail : request.getPromotions()) {
                PromotionUsage usage = PromotionUsage.builder()
                        .userId(userId)
                        .houseId(request.getHouseId())
                        .promotionId(detail.getPromotionId())
                        .promotionType(detail.getType())
                        .promotionName(detail.getName())
                        .discountAmount(detail.getDiscountAmount())
                        .originalAmount(request.getOriginalAmount())
                        .finalAmount(request.getFinalAmount())
                        .useTime(LocalDateTime.parse(detail.getUseTime(), 
                                DateTimeFormatter.ISO_DATE_TIME))
                        .status(1) // 1-已使用
                        .build();
                
                promotionUsageMapper.insert(usage);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("记录优惠使用情况失败", e);
            throw new BusinessException("记录优惠使用情况失败");
        }
    }
} 