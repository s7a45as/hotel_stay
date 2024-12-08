package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.comUtils.entity.City;
import com.homestay.modules.comUtils.entity.District;
import com.homestay.modules.comUtils.mapper.CityMapper;
import com.homestay.modules.comUtils.mapper.DistrictMapper;
import com.homestay.modules.merchant.entity.Merchant;
import com.homestay.modules.merchant.entity.MerchantHouse;
import com.homestay.modules.merchant.dto.MerchantHouseDTO;
import com.homestay.modules.merchant.enums.HouseCategoryEnum;
import com.homestay.modules.merchant.enums.HouseStatusEnum;
import com.homestay.modules.merchant.enums.HouseTypeEnum;
import com.homestay.modules.merchant.enums.MerchantStatusEnum;
import com.homestay.modules.merchant.mapper.MerchantHouseMapper;
import com.homestay.modules.merchant.mapper.MerchantMapper;
import com.homestay.modules.merchant.service.MerchantHouseService;
import com.homestay.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantHouseServiceImpl extends ServiceImpl<MerchantHouseMapper, MerchantHouse> implements MerchantHouseService {

    private final MerchantHouseMapper houseMapper;
    private final UploadUtils uploadUtils;
    private  final MerchantMapper merchantMapper;
    private  final CityMapper cityMapper;
    private  final DistrictMapper districtMapper;

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
        // 1. 参数校验
        validateHouseData(houseDTO);

        // 2. 获取当前商家信息
        Long merchantId = SecurityUtils.getCurrentUserId();
        Merchant merchant = merchantMapper.selectOne(
                new LambdaQueryWrapper<Merchant>()
                        .eq(Merchant::getId, merchantId)
                        .select(Merchant::getUsername, Merchant::getStatus)
        );

        // 3. 验证商家状态
        if (merchant == null) {
            throw new BusinessException("商家信息不存在");
        }
        if (!MerchantStatusEnum.NORMAL.getCode().equals(merchant.getStatus())) {
            throw new BusinessException("商家状态异常，无法创建房源");
        }

        try {

            LambdaQueryWrapper<City> wrapper=new LambdaQueryWrapper<City>()
                    .eq(City::getCode,houseDTO.getCity());
            LambdaQueryWrapper<District> wrapper1 =new LambdaQueryWrapper<District>()
                    .eq(District::getCode,houseDTO.getDistrict());

            City city=cityMapper.selectOne(wrapper);
            District district=districtMapper.selectOne(wrapper1);



            // 4. DTO转换为实体
            MerchantHouse house = new MerchantHouse();
            BeanUtils.copyProperties(houseDTO, house);
            // 5. 设置其他必要字段
            house.setMerchantId(merchantId);
            house.setMerchantName(merchant.getUsername());
            house.setStatus(HouseStatusEnum.ON_SHELF.getCode()); // 默认上架
            house.setRating(0.0);
            house.setReviewCount(0);
            // TODO：只是将编号改成中文存入数据库，可能会存在一定的问题，但是我不想该前端的渲染了
            house.setCity(city.getName());
            house.setDistrict(district.getName());
            house.setCategory(HouseCategoryEnum.getDescriptionByCategory(house.getCategory()));
            house.setType(HouseTypeEnum.getDescriptionByCode(house.getType()));

            // 6. 构建完整地址
            String fullAddress = String.format("%s%s%s",
                    city.getName(),
                    district.getName(),
                    houseDTO.getLocation()
            );
            house.setAddress(fullAddress);

            // 7. 处理图片和设施数据
            house.setImages(CollectionUtils.isEmpty(houseDTO.getImages()) ?
                    new ArrayList<>() : houseDTO.getImages());
            house.setFacilities(CollectionUtils.isEmpty(houseDTO.getFacilities()) ?
                    new ArrayList<>() : houseDTO.getFacilities());
            house.setFeatures(CollectionUtils.isEmpty(houseDTO.getFeatures()) ?
                    new ArrayList<>() : houseDTO.getFeatures());

            // 8. 插入数据
            if (houseMapper.insert(house) != 1) {
                throw new BusinessException(ResultCode.DATA_ADD_FAILED);
            }


        } catch (Exception e) {
            log.error("创建房源失败", e);
            throw new BusinessException("创建房源失败：" + e.getMessage());
        }
    }

    /**
     * 验证房源数据
     */
    private void validateHouseData(MerchantHouseDTO houseDTO) {
        // 1. 基础数据验证
        if (houseDTO == null) {
            throw new BusinessException("房源数据不能为空");
        }

        // 2. 价格验证
        if (houseDTO.getPrice() == null || houseDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("房源价格必须大于0");
        }

        // 3. 入住人数验证
        if (houseDTO.getMaxGuests() == null || houseDTO.getMaxGuests() <= 0) {
            throw new BusinessException("最大入住人数必须大于0");
        }

        // 4. 图片验证
        if (StringUtils.isEmpty(houseDTO.getImage())) {
            throw new BusinessException("主图不能为空");
        }
        if (CollectionUtils.isEmpty(houseDTO.getImages())) {
            throw new BusinessException("至少上传一张房源图片");
        }

        // 5. 地址验证
        if (StringUtils.isEmpty(houseDTO.getCity())
                || StringUtils.isEmpty(houseDTO.getDistrict())
                || StringUtils.isEmpty(houseDTO.getLocation())) {
            throw new BusinessException("房源地址信息不完整");
        }

        // 6. 验证房源类型是否在允许范围内
        if (!HouseTypeEnum.contains(houseDTO.getType())) {
            throw new BusinessException("无效的房源类型");
        }

        // 7. 验证分类是否在允许范围内
        if (!HouseCategoryEnum.contains(houseDTO.getCategory())) {
            throw new BusinessException("无效的房源分类");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouse(Long id, MerchantHouseDTO houseDTO) {
        MerchantHouse house = houseMapper.selectById(id);
        LambdaQueryWrapper<City> wrapper=new LambdaQueryWrapper<City>()
                .eq(City::getCode,houseDTO.getCity());
        LambdaQueryWrapper<District> wrapper1 =new LambdaQueryWrapper<District>()
                .eq(District::getCode,houseDTO.getDistrict());

        City city=cityMapper.selectOne(wrapper);
        District district=districtMapper.selectOne(wrapper1);

        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (!house.getMerchantId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }


        BeanUtils.copyProperties(houseDTO, house);
        if(city!=null)
        {
            house.setCity(city.getName());
        }
        if(district!=null)
            house.setDistrict(district.getName());

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