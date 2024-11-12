package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.shareentity.House;
import com.homestay.modules.admin.dto.HousePageDTO;
import com.homestay.modules.admin.mapper.AdminHouseMapper;
import com.homestay.modules.admin.service.AdminHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminHouseServiceImpl extends ServiceImpl<AdminHouseMapper, House> implements AdminHouseService {

    @Override
    public HousePageDTO getHouseList(Integer currentPage, Integer pageSize, String name, String type, Integer status) {
        // 构建查询条件
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), House::getName, name)
                .eq(StringUtils.hasText(type), House::getType, type)
                .eq(status != null, House::getStatus, status)
                .orderByDesc(House::getCreateTime);
        
        // 执行分页查询
        Page<House> page = new Page<>(currentPage, pageSize);
        Page<House> result = baseMapper.selectPage(page, wrapper);
        
        // 构建返回结果
        return HousePageDTO.builder()
                .list(result.getRecords())
                .total(result.getTotal())
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouse(Long id, House house) {
        // 1. 检查房源是否存在
        House existingHouse = getById(id);
        if (existingHouse == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        // 2. 更新房源信息
        house.setId(id);
        if (!updateById(house)) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouse(Long id) {
        // 1. 检查房源是否存在
        House house = getById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        // 2. 删除房源
        if (!removeById(id)) {
            throw new BusinessException(ResultCode.DATA_DELETE_FAILED);
        }
    }
} 