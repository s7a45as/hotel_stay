package com.homestay.modules.merchant.service;

import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.merchant.dto.MerchantPromotionDTO;
import java.util.List;

public interface MerchantPromotionService {

    /**
     * 获取优惠活动列表
     */
    List<MerchantPromotion> getPromotionList();

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