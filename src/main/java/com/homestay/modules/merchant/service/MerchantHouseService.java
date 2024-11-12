package com.homestay.modules.merchant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.modules.merchant.entity.MerchantHouse;
import com.homestay.modules.merchant.dto.MerchantHouseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MerchantHouseService {

    /**
     * 分页获取商家房源列表
     */
    Page<MerchantHouse> getHouseList(Integer currentPage, Integer pageSize, String name, String type, Integer status);

    /**
     * 创建房源
     */
    void createHouse(MerchantHouseDTO houseDTO);

    /**
     * 更新房源
     */
    void updateHouse(Long id, MerchantHouseDTO houseDTO);

    /**
     * 删除房源
     */
    void deleteHouse(Long id);

    /**
     * 上传房源图片
     */
    String uploadHouseImage(MultipartFile file);
} 