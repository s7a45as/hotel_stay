package com.homestay.modules.house.service;

import com.homestay.modules.house.dto.PromotionUsageRequest;

public interface PromotionUsageService {
    void recordUsage(PromotionUsageRequest request, Long userId);
} 