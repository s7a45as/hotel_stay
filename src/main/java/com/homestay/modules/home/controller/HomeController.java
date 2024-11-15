package com.homestay.modules.home.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.home.dto.HousePageDTO;
import com.homestay.modules.home.entity.homeCity;
import com.homestay.modules.home.entity.Destination;
import com.homestay.modules.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "首页", description = "首页相关接口")
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    
    @Operation(summary = "获取城市列表")
    @GetMapping("/cities")
    public Result<List<homeCity>> getCities() {
        return Result.success(homeService.getCityList());
    }
    
    @Operation(summary = "获取城市房源列表")
    @GetMapping("/houses")
    public Result<HousePageDTO> getCityHouses(
            @Parameter(description = "城市代码") @RequestParam(required = false) String cityCode,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(homeService.getHouseList(cityCode, page, size));
    }
    
    @Operation(summary = "获取热门目的地")
    @GetMapping("/popular-destinations")
    public Result<List<Destination>> getPopularDestinations() {
        return Result.success(homeService.getPopularDestinations());
    }
    
    @Operation(summary = "搜索房源")
    @GetMapping("/search")
    public Result<HousePageDTO> searchHouses(
            @Parameter(description = "关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "城市") @RequestParam(required = false) String city,
            @Parameter(description = "房源类型") @RequestParam(required = false) String type,
            @Parameter(description = "最低价格") @RequestParam(required = false) Integer minPrice,
            @Parameter(description = "最高价格") @RequestParam(required = false) Integer maxPrice,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(homeService.searchHouses(keyword, city, type, minPrice, maxPrice, page, size));
    }
} 