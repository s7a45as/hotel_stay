package com.homestay.modules.house.service;

import com.homestay.modules.house.entity.PriceCalculationResult;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceCalculationService {
    
    /**
     * 计算价格和可用优惠
     * @param houseId 房源ID
     * @param originalPrice 原始价格
     * @param checkInTime 入住时间
     * @param checkOutTime 退房时间
     * @param orderId 订单ID(可选)
     * @param userId 用户ID
     * @return 计算结果
     */
    PriceCalculationResult calculatePrice(
        Long houseId,
        BigDecimal originalPrice,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        String orderId,
        Long userId
    );
} 