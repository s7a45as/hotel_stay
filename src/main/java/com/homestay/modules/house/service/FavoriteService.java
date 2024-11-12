package com.homestay.modules.house.service;

import com.homestay.modules.house.dto.HouseListDTO;

public interface FavoriteService {
    
    /**
     * 获取收藏列表
     */
    HouseListDTO getFavoriteList(Integer page, Integer pageSize);
} 