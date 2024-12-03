package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.modules.house.entity.AppliedPromotion;
import com.homestay.modules.house.entity.PriceCalculationResult;
import com.homestay.modules.house.service.PriceCalculationService;
import com.homestay.modules.house.service.SystemPromotionService;
import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.home.entity.Promotion;
import com.homestay.modules.merchant.service.MerchantPromotionService;
import com.homestay.modules.house.entity.PromotionUsage;
import com.homestay.modules.house.mapper.PromotionUsageMapper;
import com.homestay.modules.house.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final MerchantPromotionService merchantPromotionService;
    private final SystemPromotionService systemPromotionService;
    private final MerchantService merchantService;
    private final PromotionUsageMapper promotionUsageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PriceCalculationResult calculatePrice(
            Long houseId,
            BigDecimal originalPrice,
            LocalDateTime checkInTime,
            LocalDateTime checkOutTime,
            String orderId,
            Long userId
    ) {
        // 获取房源所属商家ID
        Long merchantId = merchantService.getMerchantIdByHouseId(houseId);
        
        // 计算总价（原始价格 * 天数）
        long days = ChronoUnit.DAYS.between(checkInTime.toLocalDate(), checkOutTime.toLocalDate());
        BigDecimal totalOriginalPrice = originalPrice.multiply(BigDecimal.valueOf(days));
        
        // 获取商家优惠活动
        List<MerchantPromotion> merchantPromotions = merchantPromotionService
            .getValidPromotions(merchantId, checkInTime, checkOutTime);

        // 获取系统优惠活动
        List<Promotion> systemPromotions = systemPromotionService
            .getValidPromotions(checkInTime, checkOutTime);

        // 计算所有可用优惠
        List<AppliedPromotion> appliedPromotions = new ArrayList<>();
        BigDecimal finalPrice = totalOriginalPrice;

        // 应用商家优惠
        for (MerchantPromotion promotion : merchantPromotions) {
            // 检查用户是否已使用过此优惠
            if (hasUsedPromotion(userId, promotion.getId(), "MERCHANT")) {
                log.debug("MERCHANT：" + promotion.getName());
                continue;
            }

            BigDecimal discountAmount = calculateDiscountAmount(promotion, totalOriginalPrice);
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                appliedPromotions.add(AppliedPromotion.builder()
                        .id(promotion.getId())
                        .name(promotion.getName())
                        .type("MERCHANT")
                        .discountAmount(discountAmount)
                        .build());
                finalPrice = finalPrice.subtract(discountAmount);
            }
        }
        // 应用系统优惠
        for (Promotion promotion : systemPromotions) {
            // 检查用户是否已使用过此优惠
            if (hasUsedPromotion(userId, promotion.getId(), "SYSTEM")) {
                log.debug("SYSTEM用户已使用过此优惠：" + promotion.getTitle());
                continue;
            }

            BigDecimal discountAmount = calculateSystemDiscountAmount(promotion, totalOriginalPrice);
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                appliedPromotions.add(AppliedPromotion.builder()
                        .id(promotion.getId())
                        .name(promotion.getTitle())
                        .type("SYSTEM")
                        .discountAmount(discountAmount)
                        .build());
                finalPrice = finalPrice.subtract(discountAmount);
            }
        }
        // 按优惠金额排序
        appliedPromotions.sort((a, b) -> b.getDiscountAmount().compareTo(a.getDiscountAmount()));
        return PriceCalculationResult.builder()
            .originalPrice(totalOriginalPrice)
            .finalPrice(finalPrice)
            .discountAmount(totalOriginalPrice.subtract(finalPrice))
            .appliedPromotions(appliedPromotions)
            .build();
    }
    /**
     * 检查用户是否已使用过某个优惠
     * @param userId 用户ID
     * @param promotionId 优惠ID
     * @param promotionType 优惠类型（MERCHANT/SYSTEM）
     * @return 是否已使用
     */
    private boolean hasUsedPromotion(Long userId, Long promotionId, String promotionType) {
        if (userId == null || promotionId == null) {
            return false;
        }

        LambdaQueryWrapper<PromotionUsage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PromotionUsage::getUserId, userId)
                .eq(PromotionUsage::getPromotionId, promotionId)
                .eq(PromotionUsage::getPromotionType, promotionType)
                .eq(PromotionUsage::getStatus, 1);  // 1表示已使用

        return promotionUsageMapper.selectCount(wrapper) > 0;
    }
    private BigDecimal calculateDiscountAmount(MerchantPromotion promotion, BigDecimal originalPrice) {
        switch (promotion.getType()) {
            case "DISCOUNT":
                return originalPrice.multiply(BigDecimal.ONE.subtract(
                    promotion.getDiscount().divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP)));
            case "AMOUNT_OFF":
                if (originalPrice.compareTo(promotion.getThreshold()) >= 0) {
                    return promotion.getDiscount();
                }
                break;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateSystemDiscountAmount(Promotion promotion, BigDecimal originalPrice) {
        switch (promotion.getType()) {
            case "DISCOUNT":
                return originalPrice.multiply(BigDecimal.ONE.subtract(
                    BigDecimal.valueOf(promotion.getValue()).divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP)));
            case "COUPON":
                return BigDecimal.valueOf(promotion.getValue());
        }
        return BigDecimal.ZERO;
    }
} 