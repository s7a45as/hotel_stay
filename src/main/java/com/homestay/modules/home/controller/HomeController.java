package com.homestay.modules.home.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.home.dto.HousePageDTO;
import com.homestay.modules.home.entity.homeCity;
import com.homestay.modules.home.entity.Destination;
import com.homestay.modules.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "首页", description = "提供首页相关功能，包括城市列表、房源搜索、热门目的地等")
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    
    @Operation(summary = "获取城市列表", 
              description = "获取系统支持的所有城市列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = homeCity.class)))
    })
    @GetMapping("/cities")
    public Result<List<homeCity>> getCities() {
        return Result.success(homeService.getCityList());
    }
    
    @Operation(summary = "获取城市房源列表", 
              description = "分页获取指定城市的房源列表，如果不指定城市则获取所有城市的房源")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HousePageDTO.class))),
        @ApiResponse(responseCode = "400", description = "参数错误")
    })
    @GetMapping("/houses")
    public Result<HousePageDTO> getCityHouses(
            @Parameter(description = "城市代码，如：BJ-北京，SH-上海", example = "BJ") 
            @RequestParam(required = false) String cityCode,
            
            @Parameter(description = "页码，从1开始", example = "1") 
            @RequestParam(defaultValue = "1") Integer page,
            
            @Parameter(description = "每页数量，最大50", example = "10") 
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return Result.success(homeService.getHouseList(cityCode, page, size));
    }
    
    @Operation(summary = "获取热门目的地", 
              description = "获取系统推荐的热门旅游目的地列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Destination.class)))
    })
    @GetMapping("/popular-destinations")
    public Result<List<Destination>> getPopularDestinations() {
        return Result.success(homeService.getPopularDestinations());
    }
    
    @Operation(summary = "搜索房源", 
              description = "根据多个条件搜索房源，支持关键词、城市、类型、价格区间等条件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "搜索成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HousePageDTO.class))),
        @ApiResponse(responseCode = "400", description = "参数错误")
    })
    @GetMapping("/search")
    public Result<HousePageDTO> searchHouses(
            @Parameter(description = "搜索关键词，支持房源名称、地址等", example = "海景房") 
            @RequestParam(required = false) String keyword,
            
            @Parameter(description = "城市代码", example = "BJ") 
            @RequestParam(required = false) String city,
            
            @Parameter(description = "房源类型：APARTMENT-公寓，VILLA-别墅等", example = "APARTMENT") 
            @RequestParam(required = false) String type,
            
            @Parameter(description = "最低价格（元/晚）", example = "100") 
            @RequestParam(required = false) Integer minPrice,
            
            @Parameter(description = "最高价格（元/晚）", example = "1000") 
            @RequestParam(required = false) Integer maxPrice,
            
            @Parameter(description = "页码，从1开始", example = "1") 
            @RequestParam(defaultValue = "1") Integer page,
            
            @Parameter(description = "每页数量，最大50", example = "10") 
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return Result.success(homeService.searchHouses(keyword, city, type, minPrice, maxPrice, page, size));
    }
} 