package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.dto.BookingDTO;
import com.homestay.modules.house.dto.HouseDetailDTO;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.dto.HouseQueryDTO;
import com.homestay.modules.house.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "房源管理", description = "房源相关接口")
@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @Operation(summary = "获取房源列表")
    @GetMapping("/list")
    public Result<HouseListDTO> getHouseList(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) Integer guestCount,
        @RequestParam(required = false) String checkInDate,
        @RequestParam(required = false) String checkOutDate,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) String roomTypes,
        @RequestParam(required = false) String amenities,
        @RequestParam(required = false) String tags
    ) {
        // 将参数封装到 HouseQueryDTO 中，同时进行标准化处理
        HouseQueryDTO query = new HouseQueryDTO();
        query.setPage(page);
        query.setPageSize(pageSize);
        query.setCity(city != null ? city.replaceAll("[\"']", "").trim() : null);
        query.setGuestCount(guestCount);
        query.setCheckInDate(checkInDate);
        query.setCheckOutDate(checkOutDate);
        query.setMinPrice(minPrice);
        query.setMaxPrice(maxPrice);
        query.setRoomTypes(roomTypes != null ? roomTypes.replaceAll("[\"']", "") : null);
        query.setAmenities(amenities != null ? amenities.replaceAll("[\"']", "") : null);
        query.setTags(tags != null ? tags.replaceAll("[\"']", "") : null);
        
        return Result.success(houseService.getHouseList(query));
    }

    @Operation(summary = "获取房源详情")
    @GetMapping("/detail/{id}")
    public Result<HouseDetailDTO> getHouseDetail(@PathVariable Long id) {
        return Result.success(houseService.getHouseDetail(id));
    }

    @Operation(summary = "收藏/取消收藏房源")
    @PostMapping("/favorite/{id}")
    public Result<Void> toggleFavorite(@PathVariable Long id) {
        houseService.toggleFavorite(id);
        return Result.success();
    }

    @Operation(summary = "创建预订")
    @PostMapping("/booking")
    public Result<String> createBooking(@RequestBody BookingDTO booking) {
        String orderId = houseService.createBooking(booking);
        return Result.success(orderId);
    }

    @Operation(summary = "获取房源类型列表")
    @GetMapping("/categories")
    public Result<Object> getCategories() {
        return Result.success(houseService.getCategories());
    }
} 