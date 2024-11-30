package com.homestay.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.constant.CommonConstants;
import com.homestay.modules.comUtils.entity.City;
import com.homestay.modules.comUtils.mapper.CityMapper;
import com.homestay.modules.home.dto.HousePageDTO;
import com.homestay.modules.home.entity.homeCity;
import com.homestay.modules.home.entity.homeDistricts;
import com.homestay.common.shareentity.House;
import com.homestay.modules.home.mapper.HomeMapper;
import com.homestay.modules.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {

    private final HomeMapper homeMapper;
    private final CityMapper cityMapper;
    @Override
    @Cacheable(value = "cityList", key = "'all'", unless = "#result == null")
    public List<homeCity> getCityList() {
        return homeMapper.selectCityList();
    }

    @Override
    public HousePageDTO getHouseList(String cityCode, Integer page, Integer size) {
        // 参数校验
        page = Math.max(CommonConstants.Business.DEFAULT_PAGE_NUM, page);
        size = Math.min(CommonConstants.Business.MAX_PAGE_SIZE,
                Math.max(CommonConstants.Business.DEFAULT_PAGE_SIZE, size));

        // 构建查询条件
        log.info("cityCode:" + cityCode);
        LambdaQueryWrapper<City> wrapper2 = new LambdaQueryWrapper<City>()
                .eq(City::getCode, cityCode);
        City city = cityMapper.selectOne(wrapper2);
        log.info("city--city:" + city);

        // 获取城市名称并处理，只取前两个字
        String cityName = city.getName();
        if (cityName != null && cityName.length() > 2) {
            cityName = cityName.substring(0, 2); // 取前两个字
        }

        // 使用like进行模糊匹配
        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<House>()
                .like(cityName != null, House::getCity, cityName)
                .orderByDesc(House::getRating);

        // 分页查询
        Page<House> pageResult = homeMapper.selectPage(new Page<>(page, size), wrapper);

        // 封装返回结果
        return HousePageDTO.builder()
                .list(pageResult.getRecords())
                .total(pageResult.getTotal())
                .build();
    }



    @Override
    @Cacheable(value = "destinations", key = "'popular'", unless = "#result == null")
    public List<homeDistricts> getPopularDestinations() {
        return homeMapper.selectPopularDestinations();
    }

    @Override
    public HousePageDTO searchHouses(String keyword, String city, String type,
                                   Integer minPrice, Integer maxPrice,
                                   Integer page, Integer size) {
        // 参数校验
        page = Math.max(CommonConstants.Business.DEFAULT_PAGE_NUM, page);
        size = Math.min(CommonConstants.Business.MAX_PAGE_SIZE, 
                       Math.max(CommonConstants.Business.DEFAULT_PAGE_SIZE, size));
        
        // 创建分页参数
        Page<House> pageParam = new Page<>(page, size);
        
        // 执行分页查询
        Page<House> pageResult = homeMapper.searchHouses(pageParam, keyword, city, type, 
                                                       minPrice, maxPrice);
        
        // 封装返回结果
        return HousePageDTO.builder()
                .list(pageResult.getRecords())
                .total(pageResult.getTotal())
                .build();
    }
} 