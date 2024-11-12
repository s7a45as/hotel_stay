package com.homestay.modules.house.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface HouseImageService {
    
    /**
     * 上传房源图片
     *
     * @param houseId 房源ID
     * @param file 图片文件
     * @return 图片URL
     */
    String uploadImage(Long houseId, MultipartFile file);
    
    /**
     * 删除房源图片
     *
     * @param houseId 房源ID
     * @param imageUrl 图片URL
     */
    void deleteImage(Long houseId, String imageUrl);
    
    /**
     * 获取房源图片列表
     *
     * @param houseId 房源ID
     * @return 图片URL列表
     */
    List<String> getImageList(Long houseId);
    
    /**
     * 设置房源主图
     *
     * @param houseId 房源ID
     * @param imageUrl 图片URL
     */
    void setMainImage(Long houseId, String imageUrl);
} 