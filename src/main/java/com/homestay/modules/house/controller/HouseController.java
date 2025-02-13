package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.house.dto.BookingDTO;
import com.homestay.modules.house.dto.HouseDetailDTO;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.dto.HouseQueryDTO;
import com.homestay.modules.house.service.HouseService;
import com.homestay.modules.order.entity.UserOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Tag(name = "房源管理", description = "提供房源查询、预订、收藏等功能")
@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
@Slf4j
public class HouseController {

    private final HouseService houseService;

    @Operation(summary = "获取房源列表", 
              description = "分页获取房源列表，支持多种筛选条件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HouseListDTO.class))),
        @ApiResponse(responseCode = "400", description = "参数错误")
    })
    @GetMapping("/list")
    public Result<HouseListDTO> getHouseList(
        @Parameter(description = "页码，从1开始", example = "1") 
        @RequestParam(defaultValue = "1") Integer page,
        
        @Parameter(description = "每页数量，最大50", example = "10")
        @RequestParam(defaultValue = "10") Integer pageSize,
        
        @Parameter(description = "城市代码", example = "BJ") 
        @RequestParam(required = false) String city,
        
        @Parameter(description = "地区", example = "朝阳区") 
        @RequestParam(required = false) String district,
        
        @Parameter(description = "入住人数", example = "2") 
        @RequestParam(required = false) Integer guestCount,
        
        @Parameter(description = "入住日期，格式：yyyy-MM-dd", example = "2024-03-20") 
        @RequestParam(required = false) String checkInDate,
        
        @Parameter(description = "退房日期，格式：yyyy-MM-dd", example = "2024-03-22") 
        @RequestParam(required = false) String checkOutDate,
        
        @Parameter(description = "最低价格（元/晚）", example = "100") 
        @RequestParam(required = false) Integer minPrice,
        
        @Parameter(description = "最高价格（元/晚）", example = "1000") 
        @RequestParam(required = false) Integer maxPrice,
        
        @Parameter(description = "房型，多个用逗号分隔", example = "单人间,双人间") 
        @RequestParam(required = false) String roomTypes,
        
        @Parameter(description = "设施，多个用逗号分隔", example = "wifi,空调") 
        @RequestParam(required = false) String amenities,
        
        @Parameter(description = "标签，多个用逗号分隔", example = "海景,近地铁") 
        @RequestParam(required = false) String tags
    ) {
        // 参数验证
        if (pageSize > 50) {
            pageSize = 50;
        }
        
        // 日期格式验证
        if (StringUtils.isNotBlank(checkInDate) && StringUtils.isNotBlank(checkOutDate)) {
            try {
                log.debug("入住日期:{}", checkInDate);
                log.debug("当前时间:{}", LocalDateTime.now());
                LocalDateTime checkIn = LocalDate.parse(checkInDate).atStartOfDay();
                LocalDateTime checkOut = LocalDate.parse(checkOutDate).atStartOfDay();
                
                if (checkIn.isAfter(checkOut)) {
                    return Result.error(ResultCode.PARAM_ERROR.getCode(), "退房日期必须晚于入住日期");
                }

                if (checkIn.isBefore(LocalDateTime.now())) {

                    return Result.error(ResultCode.PARAM_ERROR.getCode(), "入住日期不能早于今天");
                }
            } catch (Exception e) {
                return Result.error(ResultCode.PARAM_ERROR.getCode(), "日期格式错误，请使用yyyy-MM-dd格式");
            }
        }

        // 价格区间验证
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            return Result.error(ResultCode.PARAM_ERROR.getCode(), "最低价格不能高于最高价格");
        }

        // 将参数封装到 HouseQueryDTO 中，同时进行标准化处理
        HouseQueryDTO query = new HouseQueryDTO();
        query.setPage(page);
        query.setPageSize(pageSize);
        query.setCity(StringUtils.trimToNull(city));
        query.setDistrict(StringUtils.trimToNull(district));
        query.setGuestCount(guestCount);
        query.setCheckInDate(checkInDate);
        query.setCheckOutDate(checkOutDate);
        query.setMinPrice(minPrice);
        query.setMaxPrice(maxPrice);
        query.setRoomTypes(StringUtils.trimToNull(roomTypes));
        query.setAmenities(StringUtils.trimToNull(amenities));
        query.setTags(StringUtils.trimToNull(tags));
        
        log.info("房源查询请求参数: {}", query);
        
        try {
            return Result.success(houseService.getHouseList(query));
        } catch (Exception e) {
            log.error("获取房源列表失败", e);
            return Result.error(ResultCode.ERROR.getCode(), "获取房源列表失败");
        }
    }

    @Operation(summary = "获取房源详情", 
              description = "获取指定房源的详细信息，包括设施、图片等")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HouseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "房源不存在")
    })
    @GetMapping("/detail/{id}")
    public Result<HouseDetailDTO> getHouseDetail(
        @Parameter(description = "房源ID", required = true, example = "1") 
        @PathVariable Long id
    ) {
        log.info("获取房源详情，ID: {}", id);
        HouseDetailDTO detail = houseService.getHouseDetail(id);
//        log.info("房源详情响应: {}", detail);
        return Result.success(detail);
    }

    @Operation(summary = "收藏/取消收藏房源", 
              description = "切换房源的收藏状态，已收藏则取消，未收藏则收藏")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "操作成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "房源不存在")
    })
    @PostMapping("/favorite/{id}")
    public Result<Void> toggleFavorite(
        @Parameter(description = "房源ID", required = true, example = "1") 
        @PathVariable Long id
    ) {
        houseService.toggleFavorite(id);
        return Result.success();
    }

    @Operation(summary = "创建预订", 
              description = "创建房源预订订单")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "预订成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "409", description = "所选日期已被预订")
    })
    @PostMapping("/booking")
    public Result<UserOrder> createBooking(
        @Parameter(description = "预订信息", required = true) 
        @RequestBody @Valid BookingDTO booking
    ) {
        log.info("创建预订请求: {}", booking);
        UserOrder success = houseService.createBooking(booking);
        return Result.success(success);

    }

    @Operation(summary = "获取房源类型列表", 
              description = "获取系统支持的所有房源类型")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", 
                    example = "[{\"label\":\"单人间\",\"value\":\"1\"}]")))
    })
    @GetMapping("/categories")
    public Result<Object> getCategories() {
        return Result.success(houseService.getCategories());
    }
} 