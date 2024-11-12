package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.shareentity.House;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.house.dto.HouseDTO;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.mapper.FavoriteMapper;
import com.homestay.modules.house.mapper.HouseMapper;
import com.homestay.modules.house.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
        List<HouseDTO> houseDTOList = result.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        // 4. 使用builder模式构建返回结果
        return HouseListDTO.builder()
                .total(result.getTotal())
                .currentPage(page)
                .pageSize(pageSize)
                .list(houseDTOList)
                .build();
    }
    
    /**
     * 将House实体转换为HouseDTO
     */
    private HouseDTO convertToDTO(House house) {
        HouseDTO dto = new HouseDTO();
        BeanUtils.copyProperties(house, dto);
        return dto;
    }
} 