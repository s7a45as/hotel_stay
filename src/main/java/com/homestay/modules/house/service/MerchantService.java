package com.homestay.modules.house.service;

public interface MerchantService {
    /**
     * 根据房源ID获取商家ID
     */
    Long getMerchantIdByHouseId(Long houseId);
} 