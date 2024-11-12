package com.homestay.modules.house.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.house.service.HouseImageService;
import com.homestay.common.shareentity.House;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseImageServiceImpl implements HouseImageService {

    private final UploadUtils uploadUtils;
    private final com.baomidou.mybatisplus.core.mapper.BaseMapper<House> houseMapper;

    @Override
    @Transactional
    public String uploadImage(Long houseId, MultipartFile file) {
        // 检查房源是否存在
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }

        // 上传图片
        String imageUrl = uploadUtils.upload(file, "house");

        // 更新房源图片列表
        List<String> images = house.getImages();
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(imageUrl);
        
        // 如果是第一张图片，设置为主图
        if (images.size() == 1) {
            house.setImage(imageUrl);
        }
        
        house.setImages(images);
        houseMapper.updateById(house);

        return imageUrl;
    }

    @Override
    @Transactional
    public void deleteImage(Long houseId, String imageUrl) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }

        List<String> images = house.getImages();
        if (images != null) {
            images.remove(imageUrl);
            house.setImages(images);
            
            // 如果删除的是主图，则设置新的主图
            if (imageUrl.equals(house.getImage())) {
                house.setImage(images.isEmpty() ? null : images.get(0));
            }
            
            houseMapper.updateById(house);
        }
    }

    @Override
    public List<String> getImageList(Long houseId) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }
        return house.getImages() != null ? house.getImages() : new ArrayList<>();
    }

    @Override
    @Transactional
    public void setMainImage(Long houseId, String imageUrl) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }

        List<String> images = house.getImages();
        if (images == null || !images.contains(imageUrl)) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "图片不存在");
        }

        house.setImage(imageUrl);
        houseMapper.updateById(house);
    }
} 