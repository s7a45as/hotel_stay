package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.shareentity.House;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.house.dto.HouseItemDTO;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.dto.TagDTO;
import com.homestay.modules.house.entity.Tag;
import com.homestay.modules.house.mapper.FavoriteMapper;
import com.homestay.modules.house.mapper.HouseMapper;
import com.homestay.modules.house.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final HouseMapper houseMapper;

    private final SecurityUtil securityUtil;

    @Override
    public HouseListDTO getFavoriteList(Integer page, Integer pageSize) {
        // 1. 获取当前用户ID
        Long currentUserId = securityUtil.getCurrentUserId();
        
        // 2. 分页查询收藏的房源
        Page<House> result = houseMapper.selectFavoritesByUserId(currentUserId, new Page<>(page, pageSize));
        
        // 3. 转换为DTO
        List<HouseItemDTO> houseDTOList = result.getRecords().stream()
                .map(house -> {
                    HouseItemDTO dto = HouseItemDTO.builder()
                            .id(house.getId())
                            .title(house.getTitle())
                            .price(house.getPrice().intValue())
                            .city(house.getCity())
                            .type(house.getType())
                            .rating(house.getRating())
                            .coverImage(house.getImage())
                            .reviewCount(house.getReviewCount())
                            .build();
                    

                    
                    // 设置标签
                    List<Tag> tags = houseMapper.selectHouseTags(house.getId());
                    if (tags != null && !tags.isEmpty()) {
                        dto.setTags(tags.stream()
                                .map(tag -> TagDTO.builder()
                                        .type(tag.getType())
                                        .text(tag.getText())
                                        .build())
                                .collect(Collectors.toList()));
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
        
        // 4. 构建返回结果
        return HouseListDTO.builder()
                .list(houseDTOList)
                .total(result.getTotal())
                .build();
    }
} 