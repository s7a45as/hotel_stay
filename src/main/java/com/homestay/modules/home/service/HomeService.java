package com.homestay.modules.home.service;

import com.homestay.modules.home.dto.HousePageDTO;
import com.homestay.modules.home.entity.homeCity;
import com.homestay.modules.home.entity.Destination;

import java.util.List;

public interface HomeService {
    
    /**
     * 获取城市列表
     */
    List<homeCity> getCityList();
    
    /**
     * 获取城市房源列表
     * @param cityCode 城市代码
     * @param page 页码
     * @param size 每页数量
     */
    HousePageDTO getHouseList(String cityCode, Integer page, Integer size);
    
    /**
     * 获取热门目的地
     */
    List<Destination> getPopularDestinations();
    
    /**
     * 搜索房源
     */
    HousePageDTO searchHouses(String keyword, String city, String type, 
                            Integer minPrice, Integer maxPrice, 
                            Integer page, Integer size);
} 