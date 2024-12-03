package com.homestay.modules.house.service.impl;

import com.homestay.modules.house.entity.AppliedPromotion;
import com.homestay.modules.house.entity.PriceCalculationResult;
import com.homestay.modules.house.service.PriceCalculationService;
import com.homestay.modules.house.service.SystemPromotionService;
import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.home.entity.Promotion;
import com.homestay.modules.merchant.service.MerchantPromotionService;
import com.homestay.modules.house.entity.PromotionUsage;
import com.homestay.modules.house.mapper.PromotionUsageMapper;
import com.homestay.modules.house.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final MerchantPromotionService merchantPromotionService;
    private final SystemPromotionService systemPromotionService;
    private final HouseService houseService;
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
        Long merchantId = houseService.getMerchantIdByHouseId(houseId);
        
        // 获取商家优惠活动
        List<MerchantPromotion> merchantPromotions = merchantPromotionService
            .getValidPromotions(merchantId, checkInTime, checkOutTime);

        // 获取系统优惠活动
        List<Promotion> systemPromotions = systemPromotionService
            .getValidPromotions(checkInTime, checkOutTime);

        // 计算所有可用优惠
        List<AppliedPromotion> appliedPromotions = new ArrayList<>();
        BigDecimal finalPrice = originalPrice;

        // 应用商家优惠
        for (MerchantPromotion promotion : merchantPromotions) {
            BigDecimal discountAmount = calculateDiscountAmount(promotion, originalPrice);
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                appliedPromotions.add(AppliedPromotion.builder()
                    .id(promotion.getId())
                    .name(promotion.getName())
                    .type(promotion.getType())
                    .discountAmount(discountAmount)
                    .build());
                finalPrice = finalPrice.subtract(discountAmount);
                
                // 记录优惠使用
                if (orderId != null) {
                    savePromotionUsage(userId, orderId, promotion.getId(), "MERCHANT",
                        promotion.getName(), discountAmount, originalPrice, finalPrice);
                }
            }
        }

        // 应用系统优惠
        for (Promotion promotion : systemPromotions) {
            BigDecimal discountAmount = calculateSystemDiscountAmount(promotion, originalPrice);
            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                appliedPromotions.add(AppliedPromotion.builder()
                    .id(promotion.getId())
                    .name(promotion.getTitle())
                    .type(promotion.getType())
                    .discountAmount(discountAmount)
                    .build());
                finalPrice = finalPrice.subtract(discountAmount);
                
                // 记录优惠使用
                if (orderId != null) {
                    savePromotionUsage(userId, orderId, promotion.getId(), "SYSTEM",
                        promotion.getTitle(), discountAmount, originalPrice, finalPrice);
                }
            }
        }

        // 按优惠金额排序
        appliedPromotions.sort((a, b) -> b.getDiscountAmount().compareTo(a.getDiscountAmount()));

        return PriceCalculationResult.builder()
            .originalPrice(originalPrice)
            .finalPrice(finalPrice)
            .discountAmount(originalPrice.subtract(finalPrice))
            .appliedPromotions(appliedPromotions)
            .build();
    }

    /**
     * 保存优惠活动使用记录
     */
    private void savePromotionUsage(
            Long userId, String orderId, Long promotionId, String promotionType,
            String promotionName, BigDecimal discountAmount,
            BigDecimal originalAmount, BigDecimal finalAmount) {
        PromotionUsage usage = PromotionUsage.builder()
            .userId(userId)
            .orderId(orderId)
            .promotionId(promotionId)
            .promotionType(promotionType)
            .promotionName(promotionName)
            .discountAmount(discountAmount)
            .originalAmount(originalAmount)
            .finalAmount(finalAmount)
            .useTime(LocalDateTime.now())
            .status(1)
            .build();
        promotionUsageMapper.insert(usage);
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