package com.homestay.modules.house.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.shareentity.House;
import com.homestay.modules.house.mapper.HouseMapper;
import com.homestay.modules.house.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final HouseMapper houseMapper;

    @Override
    public Long getMerchantIdByHouseId(Long houseId) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }
        return house.getMerchantId();
    }
} 