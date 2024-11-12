package com.homestay.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.common.shareentity.House;
import com.homestay.modules.admin.dto.HousePageDTO;

public interface AdminHouseService extends IService<House> {
    
    /**
     * 获取房源列表
     */
    HousePageDTO getHouseList(Integer currentPage, Integer pageSize, String name, String type, Integer status);
    
    /**
     * 更新房源信息
     */
    void updateHouse(Long id, House house);
    
    /**
     * 删除房源
     */
    void deleteHouse(Long id);
} 