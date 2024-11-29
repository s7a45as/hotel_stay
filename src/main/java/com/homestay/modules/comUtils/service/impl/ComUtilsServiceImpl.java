package com.homestay.modules.comUtils.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.modules.comUtils.entity.City;
import com.homestay.modules.comUtils.entity.District;
import com.homestay.modules.comUtils.mapper.CityMapper;
import com.homestay.modules.comUtils.mapper.DistrictMapper;
import com.homestay.modules.comUtils.service.ComUtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComUtilsServiceImpl implements ComUtilsService {

    private final CityMapper cityMapper;
    private final DistrictMapper districtMapper;

    @Override
    public List<Map<String, String>> getCityList() {
        List<City> cities = cityMapper.selectList(null);
        return cities.stream()
            .map(city -> Map.of(
                "value", city.getCode(),
                "label", city.getName(),
                "code", city.getCode()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, String>> getDistrictList(String cityCode) {
        LambdaQueryWrapper<District> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(District::getCityCode, cityCode);
        
        List<District> districts = districtMapper.selectList(wrapper);
        return districts.stream()
            .map(district -> Map.of(
                "value", district.getCode(),
                "label", district.getName(),
                "code", district.getCode()
            ))
            .collect(Collectors.toList());
    }
} 