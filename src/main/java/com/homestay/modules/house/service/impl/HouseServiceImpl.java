package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.shareentity.House;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.comUtils.entity.City;
import com.homestay.modules.comUtils.mapper.CityMapper;
import com.homestay.modules.house.dto.*;
import com.homestay.modules.house.entity.Favorite;
import com.homestay.modules.house.entity.HouseImage;
import com.homestay.modules.house.mapper.HouseMapper;
import com.homestay.modules.house.mapper.FavoriteMapper;
import com.homestay.modules.house.mapper.HouseImageMapper;
import com.homestay.modules.house.mapper.HouseFacilityMapper;
import com.homestay.modules.house.service.HouseService;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    private final HouseImageMapper houseImageMapper;
    private final HouseFacilityMapper houseFacilityMapper;
    private final FavoriteMapper favoriteMapper;
    private final SecurityUtil securityUtil; // 用于获取当前登录用户
    private final HouseMapper houseMapper;
    private  final OrderMapper  orderMapper; // 用于创建预订单

    private final CityMapper cityMapper;

    @Override
    public HouseListDTO getHouseList(HouseQueryDTO query) {

        log.info("getHouseList: {}", query);

        // 构建查询条件
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();

        // 获取城市名称
        LambdaQueryWrapper<City> wrapper1 = new LambdaQueryWrapper<City>()
                .eq(City::getCode, query.getCity())
                .select(City::getName);
        String cityName = cityMapper.selectOne(wrapper1).getName();
        if (cityName != null && cityName.length() > 2) {
            cityName = cityName.substring(0, 2); // 取前两个字
        }
        query.setCity(cityName);

        // 城市筛选
        if (query.getCity() != null) {
            String city = query.getCity().replaceAll("[\"']", "").trim();
            wrapper.like(House::getCity, city);
        }

        // 价格范围筛选
        wrapper.ge(query.getMinPrice() != null, House::getPrice, query.getMinPrice())
                .le(query.getMaxPrice() != null, House::getPrice, query.getMaxPrice());

        // 入住人数筛选
        wrapper.ge(query.getGuestCount() != null, House::getMaxGuests, query.getGuestCount());

        // 房型筛选
        if (query.getRoomTypes() != null && !query.getRoomTypes().isEmpty()) {
            String cleanRoomTypes = query.getRoomTypes().replaceAll("[\"']", "");
            List<String> roomTypeList = Arrays.asList(cleanRoomTypes.split(","));
            wrapper.in(House::getType, roomTypeList);
        }

        // 设施筛选 - 使用 JSON_CONTAINS 检查是否包含指定设施
        if (query.getAmenities() != null && !query.getAmenities().isEmpty()) {
            String cleanAmenities = query.getAmenities().replaceAll("[\"']", "");
            List<String> amenityList = Arrays.asList(cleanAmenities.split(","));
            if (!amenityList.isEmpty()) {
                // 遍历设施列表，使用 JSON_CONTAINS 来检查每项设施是否存在于数据库的 JSON 数组中
                for (String amenity : amenityList) {
                    wrapper.and(wrapper2 -> wrapper2.like(House::getFacilities, amenity));
                }
            }
        }

        // 标签筛选 - 使用 JSON_CONTAINS 检查是否包含指定标签
        if (query.getTags() != null && !query.getTags().isEmpty()) {
            String cleanTags = query.getTags().replaceAll("[\"']", "");
            List<String> tagList = Arrays.asList(cleanTags.split(","));
            if (!tagList.isEmpty()) {
                // 遍历标签列表，使用 JSON_CONTAINS 来检查每个标签是否存在于数据库的 JSON 数组中
                for (String tag : tagList) {
                    wrapper.and(wrapper2 -> wrapper2.like(House::getFeatures, tag));
                }
            }
        }

        // 日期可用性筛选
        if (query.getCheckInDate() != null && query.getCheckOutDate() != null) {
            wrapper.notExists(
                    "SELECT 1 FROM house_booking hb WHERE hb.house_id = house.id " +
                            "AND hb.status != -1 " + // 排除已取消的订单
                            "AND NOT (hb.check_out_time <= '" + query.getCheckInDate() +
                            "' OR hb.check_in_time >= '" + query.getCheckOutDate() + "')"
            );
        }

        // 分页查询
        Page<House> page = new Page<>(query.getPage(), query.getPageSize());
        Page<House> result = houseMapper.selectPage(page, wrapper);

        // 转换为 DTO
        List<HouseItemDTO> houseDTOList = result.getRecords().stream()
                .map(house -> {
                    HouseItemDTO dto = HouseItemDTO.builder()
                            .id(house.getId())
                            .title(house.getTitle())
                            .price(house.getPrice().intValue())
                            .city(house.getCity())
                            .type(house.getType())
                            .rating(house.getRating())
                            .reviewCount(house.getReviewCount())
                            .build();

                    // 设置封面图
                    HouseImage coverImage = houseImageMapper.selectCoverImage(house.getId());
                    if (coverImage != null) {
                        dto.setCoverImage(coverImage.getUrl());
                    }

                    // 设置标签
                    List<TagDTO> tags = getHouseTags(house.getId());
                    dto.setTags(tags);

                    return dto;
                })
                .collect(Collectors.toList());

        return HouseListDTO.builder()
                .list(houseDTOList)
                .total(result.getTotal())
                .build();
    }

    // 辅助方法：获取房源标签
    private List<TagDTO> getHouseTags(Long houseId) {
        // 这里需要实现从数据库查询房源标签的逻辑
        // 示例实现：
        return houseMapper.selectHouseTags(houseId).stream()
                .map(tag -> {
                    TagDTO tagDTO = new TagDTO();
                    tagDTO.setType(tag.getType());
                    tagDTO.setText(tag.getText());
                    return tagDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public HouseDetailDTO getHouseDetail(Long id) {
        // 1. 获取房源基本信息
        House house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
//        // 2. 获取房源图片
//        List<HouseImage> images = houseImageMapper.selectByHouseId(id);
//        house.setImages(images.stream().map(HouseImage::getUrl).collect(Collectors.toList()));
//
//        // 3. 获取房源设施
//        List<Facility> facilities = houseFacilityMapper.selectFacilitiesByHouseId(id);
//        // 将Facility对象转换为设施名称列表
//        house.setFacilities(facilities.stream()
//                .map(Facility::getName)  // 假设Facility类有getName()方法
//                .collect(Collectors.toList()));
//
//        // 4. 获取当前用户ID
//        Long currentUserId = securityUtil.getCurrentUserId();
//
//        // 5. ��询是否已收藏
//        boolean isFavorite = false;
//        if (currentUserId != null) {
//            isFavorite = favoriteMapper.checkFavorite(currentUserId, id) > 0;
//        }
//
//        // 6. 获取收藏数量
//        int favoriteCount = favoriteMapper.getFavoriteCount(id);
        
        return HouseDetailDTO.builder()
                .house(house)
//                .isFavorite(isFavorite)
//                .favoriteCount(favoriteCount)
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
    public boolean createBooking(BookingDTO bookingDTO) {
        try {
            // 1. 获取当前用户ID并验证
            Long currentUserId = securityUtil.getCurrentUserId();
            if (currentUserId == null) {
                throw new BusinessException(ResultCode.UNAUTHORIZED, "用户未登录");
            }

            // 2. 验证房源是否存在并检查可用性
            House house = this.getById(bookingDTO.getHouseId());
            if (house == null) {
                throw new BusinessException(ResultCode.DATA_NOT_EXIST, "房源不存在");
            }

            // 3. 验证入住人数
            if (bookingDTO.getGuestCount() > house.getMaxGuests()) {
                throw new BusinessException(ResultCode.VALIDATE_FAILED, 
                    String.format("超出最大入住人数限制,最多允许%d人入住", house.getMaxGuests()));
            }

            // 4. 验证入住日期
            long days = ChronoUnit.DAYS.between(
                bookingDTO.getCheckInTime().toLocalDate(), 
                bookingDTO.getCheckOutTime().toLocalDate()
            );
            if (days <= 0) {
                throw new BusinessException(ResultCode.VALIDATE_FAILED, "退房时间必须大于入住时间");
            }
            if (days > 90) { // 假设最大入住天数为90天
                throw new BusinessException(ResultCode.VALIDATE_FAILED, "最大入住天数不能超过90天");
            }

            // 5. 验证日期是否可预订
            LambdaQueryWrapper<UserOrder> orderWrapper = new LambdaQueryWrapper<>();
            orderWrapper.eq(UserOrder::getHouseId, bookingDTO.getHouseId())
                       .ne(UserOrder::getStatus, 2) // 排除已取消的订单
                       .and(w -> w.between(UserOrder::getCheckIn, 
                                         bookingDTO.getCheckInTime().toLocalDate(),
                                         bookingDTO.getCheckOutTime().toLocalDate())
                               .or()
                               .between(UserOrder::getCheckOut,
                                      bookingDTO.getCheckInTime().toLocalDate(),
                                      bookingDTO.getCheckOutTime().toLocalDate()));
            
            Long count = orderMapper.selectCount(orderWrapper);
            if (count > 0) {
                throw new BusinessException(ResultCode.VALIDATE_FAILED, "所选日期已被预订");
            }

            // 6. 验证订单金额
            BigDecimal expectedAmount = house.getPrice().multiply(BigDecimal.valueOf(days));
            if (expectedAmount.compareTo(bookingDTO.getAmount()) != 0) {
                throw new BusinessException(ResultCode.VALIDATE_FAILED, 
                    String.format("订单金额计算有误，期望金额：%s，实际金额：%s", 
                        expectedAmount.toString(), bookingDTO.getAmount().toString()));
            }

            // 7. 创建订单实体
            UserOrder userOrder = new UserOrder();
            userOrder.setId(generateOrderId());
            userOrder.setUserId(currentUserId);
            userOrder.setHouseId(bookingDTO.getHouseId());
            userOrder.setAmount(bookingDTO.getAmount());  // 直接使用 BookingDTO 中的 BigDecimal
            userOrder.setCheckIn(bookingDTO.getCheckInTime().toLocalDate());
            userOrder.setCheckOut(bookingDTO.getCheckOutTime().toLocalDate());
            userOrder.setNights((int) days);
            userOrder.setGuests(bookingDTO.getGuestCount());
            userOrder.setContactName(bookingDTO.getContactName());
            userOrder.setContactPhone(bookingDTO.getContactPhone());
            userOrder.setSpecialRequests(bookingDTO.getSpecialRequests());
            userOrder.setStatus(0); // 设置初始状态为待支付
            userOrder.setCreateTime(LocalDateTime.now());
            userOrder.setUpdateTime(LocalDateTime.now());

            // 8. 保存订单到数据库
            int result = orderMapper.insert(userOrder);
            return result > 0;

        } catch (BusinessException e) {
            // 业务异常直接抛出,由全局异常处理器处理
            throw e;
        } catch (Exception e) {
            // 其他异常包装成BusinessException
            log.error("创建订单失败: ", e);
            throw new BusinessException(ResultCode.ERROR, "创建订单失败", e);
        }
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