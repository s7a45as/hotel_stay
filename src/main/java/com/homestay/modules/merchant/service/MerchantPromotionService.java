package com.homestay.modules.merchant.service;

import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.merchant.dto.MerchantPromotionDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface MerchantPromotionService {

    /**
     * 获取优惠活动列表
     */
    List<MerchantPromotion> getPromotionList();

    /**
     * 获取有效的优惠活动
     * @param merchantId 商家ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 有效的优惠活动列表
     */
    List<MerchantPromotion> getValidPromotions(Long merchantId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 创建优惠活动
     */
    void createPromotion(MerchantPromotionDTO promotionDTO);

    /**
     * 更新优惠活动
     */
    void updatePromotion(Long id, MerchantPromotionDTO promotionDTO);

    /**
     * 删除优惠活动
     */
    void deletePromotion(Long id);
} 