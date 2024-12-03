package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.shareentity.House;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.comUtils.entity.City;
import com.homestay.modules.comUtils.entity.District;
import com.homestay.modules.comUtils.mapper.CityMapper;
import com.homestay.modules.comUtils.mapper.DistrictMapper;
import com.homestay.modules.house.dto.*;
import com.homestay.modules.house.entity.Favorite;
import com.homestay.modules.house.entity.PriceCalculationResult;
import com.homestay.modules.house.mapper.HouseMapper;
import com.homestay.modules.house.mapper.FavoriteMapper;
import com.homestay.modules.house.mapper.HouseFacilityMapper;
import com.homestay.modules.house.service.HouseService;
import com.homestay.modules.house.service.PriceCalculationService;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    private final HouseFacilityMapper houseFacilityMapper;
    private final FavoriteMapper favoriteMapper;
    private final SecurityUtil securityUtil; // 用于获取当前登录用户
    private final HouseMapper houseMapper;
    private  final OrderMapper  orderMapper; // 用于创建预订单
    private final DistrictMapper districtMapper;
    private final CityMapper cityMapper;
    private final ObjectMapper objectMapper;
    private final PriceCalculationService priceCalculationService;
    @Override
    public HouseListDTO getHouseList(HouseQueryDTO query) {
        log.info("getHouseList: {}", query);

        // 构建查询条件
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        
        // 基础条件：未删除且上架状态
        wrapper.eq(House::getDeleted, 0)
               .eq(House::getStatus, 1);

        // 城市和地区查询
        if (StringUtils.isNotBlank(query.getCity())) {
            // 获取城市名称
            LambdaQueryWrapper<City> cityWrapper = new LambdaQueryWrapper<City>()
                    .eq(City::getCode, query.getCity())
                    .select(City::getName);
            City city = cityMapper.selectOne(cityWrapper);
            if (city != null) {
                String cityName = city.getName();
                if (cityName != null && cityName.length() > 2) {
                    cityName = cityName.substring(0, 2); // 取前两个字
                }
                wrapper.like(House::getCity, cityName);
                
                // 地区查询
                if (StringUtils.isNotBlank(query.getDistrict())) {
                    // 使用districtMapper查询地区信息
                    LambdaQueryWrapper<District> districtWrapper = new LambdaQueryWrapper<District>()
                            .eq(District::getCityCode, query.getCity())  // 使用城市代码
                            .eq(District::getCode, query.getDistrict())  // 地区代码
                            .select(District::getName);
                    
                    District district = districtMapper.selectOne(districtWrapper);
                    log.debug("district: " + district);
                    if (district != null) {
                        String districtName = district.getName();
                        if (districtName != null && districtName.length() > 2) {
                            districtName = districtName.substring(0, 2); // 取前两个字
                        }
                        wrapper.like(House::getDistrict, districtName);
                    }
                }
            }
        }


        // 入住人数筛选
        if (query.getGuestCount() != null) {
            wrapper.ge(House::getMaxGuests, query.getGuestCount());
        }

        // 价格区间筛选
        if (query.getMinPrice() != null) {
            wrapper.ge(House::getPrice, query.getMinPrice());
        }
        if (query.getMaxPrice() != null) {
            wrapper.le(House::getPrice, query.getMaxPrice());
        }

        // 房型筛选
        if (StringUtils.isNotBlank(query.getRoomTypes())) {
            List<String> types = Arrays.asList(query.getRoomTypes().split(","));
            wrapper.in(House::getType, types);
        }

        // 日期可用性筛选（使用EXISTS子查询优化性能）
        if (StringUtils.isNotBlank(query.getCheckInDate()) && StringUtils.isNotBlank(query.getCheckOutDate())) {
            wrapper.notExists(String.format(
                "SELECT 1 FROM t_house_booking b WHERE b.house_id = t_house.id " +
                "AND b.status = 0 " + // 0表示已预订状态
                "AND NOT (b.booking_end <= '%s' OR b.booking_start >= '%s')",
                query.getCheckInDate(), query.getCheckOutDate()
            ));
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
                            .coverImage(house.getImage()) // 使用主图作为封面
                            .price(house.getPrice().intValue())
                            .city(house.getCity())
                            .district(house.getDistrict())
                            .type(house.getType())
                            .rating(house.getRating().doubleValue())
                            .reviewCount(house.getReviewCount())
                            .build();
                    
                    // 获取房源特色标签（从JSON字段）
                    if (house.getFeatures() != null) {
                        try {
                            List<TagDTO> tags = objectMapper.readValue(
                                house.getFeatures().toString(),
                                new TypeReference<List<Map<String, String>>>() {}
                            ).stream()
                            .map(tag -> TagDTO.builder()
                                    .type(tag.get("type"))
                                    .text(tag.get("text"))
                                    .build())
                            .collect(Collectors.toList());
                            dto.setTags(tags);
                        } catch (JsonProcessingException e) {
                            log.error("解析房源特色标签失败: {}", e.getMessage());
                        }
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());

        return HouseListDTO.builder()
                .list(houseDTOList)
                .total(result.getTotal())
                .build();
    }

    @Override
    public HouseDetailDTO getHouseDetail(Long id) {
        // 1. 获取房源基本信息
        House house = houseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        Long currentUserId = securityUtil.getCurrentUserId();

        // 5. 是否已收藏
        boolean isFavorite = false;
        if (currentUserId != null) {
            isFavorite = favoriteMapper.checkFavorite(currentUserId, id) > 0;
        }

        // 6. 获取收藏数量
        int favoriteCount = favoriteMapper.getFavoriteCount(id);
        log.debug("收藏数量：{}", favoriteCount);
        HouseDetailDTO houseDetailDTO= HouseDetailDTO.builder()
                .house(house)
                .isFavorite(isFavorite)
                .favoriteCount(favoriteCount)
                .build();
        log.debug("房源详情：{}", houseDetailDTO);
        return houseDetailDTO;
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
        
        // 4. 果已收藏则取消收藏，否则添加收藏
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
    public UserOrder createBooking(BookingDTO bookingDTO) {
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
            // 计算优惠后的金额
            PriceCalculationResult priceResult = priceCalculationService.calculatePrice(
                bookingDTO.getHouseId(),
                expectedAmount,
                bookingDTO.getCheckInTime(),
                bookingDTO.getCheckOutTime(),
                null,  // 创建订单时还没有订单ID
                currentUserId
            );

            if (priceResult.getFinalPrice().compareTo(bookingDTO.getAmount()) != 0) {
                throw new BusinessException(ResultCode.VALIDATE_FAILED, 
                    String.format("订单金额计算有误，期望金额：%s，实际金额：%s", 
                        priceResult.getFinalPrice().toString(), 
                        bookingDTO.getAmount().toString()));
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
            if(result>0)
            return userOrder;
            return null;

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

    @Override
    public Long getMerchantIdByHouseId(Long houseId) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }
        return house.getMerchantId();
    }
} 