package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.merchant.entity.MerchantHouse;
import com.homestay.modules.merchant.dto.MerchantHouseDTO;
import com.homestay.modules.merchant.mapper.MerchantHouseMapper;
import com.homestay.modules.merchant.service.MerchantHouseService;
import com.homestay.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MerchantHouseServiceImpl implements MerchantHouseService {

    private final MerchantHouseMapper houseMapper;
    private final UploadUtils uploadUtils;

    @Override
    public Page<MerchantHouse> getHouseList(Integer currentPage, Integer pageSize, String name, String type, Integer status) {
        Page<MerchantHouse> page = new Page<>(currentPage, pageSize);
        
        LambdaQueryWrapper<MerchantHouse> wrapper = new LambdaQueryWrapper<MerchantHouse>()
            .eq(MerchantHouse::getMerchantId, SecurityUtils.getCurrentUserId())
            .like(StringUtils.hasText(name), MerchantHouse::getName, name)
            .eq(StringUtils.hasText(type), MerchantHouse::getType, type)
            .eq(status != null, MerchantHouse::getStatus, status)
            .orderByDesc(MerchantHouse::getCreateTime);
            
        return houseMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createHouse(MerchantHouseDTO houseDTO) {
        MerchantHouse house = new MerchantHouse();
        BeanUtils.copyProperties(houseDTO, house);
        house.setMerchantId(SecurityUtils.getCurrentUserId());
        house.setStatus(1); // 默认上架
        
        if (houseMapper.insert(house) != 1) {
            throw new BusinessException(ResultCode.DATA_ADD_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouse(Long id, MerchantHouseDTO houseDTO) {
        MerchantHouse house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (!house.getMerchantId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        
        BeanUtils.copyProperties(houseDTO, house);
        if (houseMapper.updateById(house) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouse(Long id) {
        MerchantHouse house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (!house.getMerchantId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        
        if (houseMapper.deleteById(id) != 1) {
            throw new BusinessException(ResultCode.DATA_DELETE_FAILED);
        }
    }

    @Override
    public String uploadHouseImage(MultipartFile file) {
        return uploadUtils.upload(file, "house");
    }
} 