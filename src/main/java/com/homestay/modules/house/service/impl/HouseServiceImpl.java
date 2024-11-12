package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.shareentity.House;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.house.dto.*;
import com.homestay.modules.house.entity.houseBooking;
import com.homestay.modules.house.entity.Favorite;
import com.homestay.modules.house.entity.HouseImage;
import com.homestay.modules.house.entity.Facility;
import com.homestay.modules.house.mapper.BookingMapper;
import com.homestay.modules.house.mapper.HouseMapper;
import com.homestay.modules.house.mapper.FavoriteMapper;
import com.homestay.modules.house.mapper.HouseImageMapper;
import com.homestay.modules.house.mapper.HouseFacilityMapper;
import com.homestay.modules.house.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    private final HouseImageMapper houseImageMapper;
    private final HouseFacilityMapper houseFacilityMapper;
    private final FavoriteMapper favoriteMapper;
    private final BookingMapper bookingMapper;
    private final SecurityUtil securityUtil; // 用于获取当前登录用户
    private final HouseMapper houseMapper;

    @Override
    public HouseListDTO getHouseList(HouseQueryDTO query) {
        // 构建查询条件
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getCategoryId() != null, House::getCategory, query.getCategoryId())
                .like(query.getLocation() != null, House::getLocation, query.getLocation());
                
        // 价格范围查询
        if (query.getPriceRange() != null && query.getPriceRange().length == 2) {
            wrapper.between(House::getPrice, query.getPriceRange()[0], query.getPriceRange()[1]);
        }
        
        // 排序
        if ("price-asc".equals(query.getSortBy())) {
            wrapper.orderByAsc(House::getPrice);
        } else if ("price-desc".equals(query.getSortBy())) {
            wrapper.orderByDesc(House::getPrice);
        } else {
            wrapper.orderByDesc(House::getCreateTime);
        }
        
        // 分页查询
        Page<House> page = new Page<>(query.getPage(), query.getPageSize());
        Page<House> result = houseMapper.selectPage(page, wrapper);
        
        // 查询结果处理并转换为DTO
        List<HouseDTO> houseDTOList = result.getRecords().stream()
                .map(house -> {
                    HouseDTO dto = new HouseDTO();
                    BeanUtils.copyProperties(house, dto);
                    
                    // 设置封面图
                    HouseImage coverImage = houseImageMapper.selectCoverImage(house.getId());
                    if (coverImage != null) {
                        dto.setImage(coverImage.getUrl());
                    }
                    
                    // 设置设施列表
                    List<Facility> facilities = houseFacilityMapper.selectFacilitiesByHouseId(house.getId());
                    // 这里需要根据实际情况处理设施信息的转换
                    
                    return dto;
                })
                .collect(Collectors.toList());
        
        return HouseListDTO.builder()
                .list(houseDTOList)  // 使用转换后的DTO列表
                .total(result.getTotal())
                .currentPage(query.getPage())
                .pageSize(query.getPageSize())
                .build();
    }

    @Override
    public HouseDetailDTO getHouseDetail(Long id) {
        // 1. 获取房源基本信息
        House house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        // 2. 获取房源图片
        List<HouseImage> images = houseImageMapper.selectByHouseId(id);
        house.setImages(images.stream().map(HouseImage::getUrl).collect(Collectors.toList()));
        
        // 3. 获取房源设施
        List<Facility> facilities = houseFacilityMapper.selectFacilitiesByHouseId(id);
        // 将Facility对象转换为设施名称列表
        house.setFacilities(facilities.stream()
                .map(Facility::getName)  // 假设Facility类有getName()方法
                .collect(Collectors.toList()));
        
        // 4. 获取当前用户ID
        Long currentUserId = securityUtil.getCurrentUserId();
        
        // 5. 查询是否已收藏
        boolean isFavorite = false;
        if (currentUserId != null) {
            isFavorite = favoriteMapper.checkFavorite(currentUserId, id) > 0;
        }
        
        // 6. 获取收藏数量
        int favoriteCount = favoriteMapper.getFavoriteCount(id);
        
        return HouseDetailDTO.builder()
                .house(house)
                .isFavorite(isFavorite)
                .favoriteCount(favoriteCount)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleFavorite(Long id) {
        // 1. 检查房源是否存在
        House house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        // 2. 获取当前用户ID
        Long currentUserId = securityUtil.getCurrentUserId();
        if (currentUserId == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        
        // 3. 查询是否已收藏
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, currentUserId)
               .eq(Favorite::getHouseId, id);
        
        Favorite favorite = favoriteMapper.selectOne(wrapper);
        
        // 4. 如果已收藏则取消收藏，否则添加收藏
        if (favorite != null) {
            favoriteMapper.deleteById(favorite.getId());
        } else {
            favorite = new Favorite();
            favorite.setUserId(currentUserId);
            favorite.setHouseId(id);
            favoriteMapper.insert(favorite);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createBooking(BookingDTO bookingDTO) {
        // 1. 参数校验
        if (bookingDTO.getCheckOutTime().isBefore(bookingDTO.getCheckInTime())) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "退房时间不能早于入住时间");
        }
        
        // 2. 检查房源是否存在
        House house = houseMapper.selectById(bookingDTO.getHouseId());
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST, "房源不存在");
        }
        
        // 3. 检查房源状态
        if (house.getStatus() != 1) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "房源已下架");
        }
        
        // 4. 检查入住人数是否超出限制
        if (bookingDTO.getGuestCount() > house.getMaxGuests()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED, "入住人数超出房源限制");
        }
        
        // 5. 检查时间冲突
        int conflictCount = bookingMapper.checkBookingConflict(
            bookingDTO.getHouseId(), 
            bookingDTO.getCheckInTime(), 
            bookingDTO.getCheckOutTime()
        );
        if (conflictCount > 0) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "所选时间段已被预订");
        }
        
        // 6. 计算订单总价
        long days = ChronoUnit.DAYS.between(
            bookingDTO.getCheckInTime().toLocalDate(), 
            bookingDTO.getCheckOutTime().toLocalDate()
        );
        BigDecimal totalPrice = house.getPrice().multiply(new BigDecimal(days));
        
        // 7. 创建预订记录
        houseBooking houseBooking = new houseBooking();
        houseBooking.setId(generateOrderId());
        houseBooking.setHouseId(bookingDTO.getHouseId());
        houseBooking.setUserId(securityUtil.getCurrentUserId());
        houseBooking.setCheckInTime(bookingDTO.getCheckInTime());
        houseBooking.setCheckOutTime(bookingDTO.getCheckOutTime());
        houseBooking.setGuestCount(bookingDTO.getGuestCount());
        houseBooking.setContactName(bookingDTO.getContactName());
        houseBooking.setContactPhone(bookingDTO.getContactPhone());
        houseBooking.setSpecialRequests(bookingDTO.getSpecialRequests());
        houseBooking.setTotalPrice(totalPrice);
        houseBooking.setStatus(0); // 待支付状态
        
        bookingMapper.insert(houseBooking);
        
        return houseBooking.getId();
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderId() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    public Object getCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        
        Map<String, Object> category1 = new HashMap<>();
        category1.put("label", "单人间");
        category1.put("value", "1");
        categories.add(category1);
        
        Map<String, Object> category2 = new HashMap<>();
        category2.put("label", "双人间");
        category2.put("value", "2");
        categories.add(category2);
        
        Map<String, Object> category3 = new HashMap<>();
        category3.put("label", "家庭房");
        category3.put("value", "3");
        categories.add(category3);
        
        Map<String, Object> category4 = new HashMap<>();
        category4.put("label", "别墅");
        category4.put("value", "4");
        categories.add(category4);
        
        return categories;
    }
} 