package com.homestay.modules.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.common.shareentity.House;
import com.homestay.modules.house.dto.BookingDTO;
import com.homestay.modules.house.dto.HouseDetailDTO;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.dto.HouseQueryDTO;

/**
 * 房源服务接口
 */
public interface HouseService extends IService<House> {
    
    /**
     * 获取房源列表
     */
    HouseListDTO getHouseList(HouseQueryDTO query);
    
    /**
     * 获取房源详情
     */
    HouseDetailDTO getHouseDetail(Long id);
    
    /**
     * 切换收藏状态
     */
    void toggleFavorite(Long id);
    
    /**
     * 创建预订
     */
    String createBooking(BookingDTO bookingDTO);
    
    /**
     * 获取房源分类
     */
    Object getCategories();
} 