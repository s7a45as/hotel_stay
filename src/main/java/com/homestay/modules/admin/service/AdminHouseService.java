package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.HouseCreateDTO;
import com.homestay.modules.admin.dto.HousePageDTO;
import com.homestay.modules.admin.dto.HouseUpdateDTO;

public interface AdminHouseService {

    /**
     * 获取房源列表
     */
    HousePageDTO getHouseList(Integer currentPage, Integer pageSize, String name, String type, Integer status);

    /**
     * 创建房源
     */
    void createHouse(HouseCreateDTO house);

    /**
     * 更新房源
     */
    void updateHouse(Long id, HouseUpdateDTO house);

    /**
     * 删除房源
     */
    void deleteHouse(Long id);

    /**
     * 导出房源数据
     */
    byte[] exportHouses(String name, String type, Integer status);
} 