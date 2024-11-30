package com.homestay.modules.merchant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.Result;
import com.homestay.modules.merchant.entity.MerchantHouse;
import com.homestay.modules.merchant.dto.MerchantHouseDTO;
import com.homestay.modules.merchant.enums.HouseCategoryEnum;
import com.homestay.modules.merchant.service.MerchantHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@Tag(name = "商家后台-房源管理", description = "提供商家房源的增删改查功能")
@RestController
@RequestMapping("/merchant/houses")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@Slf4j
public class MerchantHouseController {

    private final MerchantHouseService houseService;

    @Operation(summary = "获取房源列表", 
              description = "分页获取商家的房源列表，支持按名称、类型和状态筛选")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping
    public Result<Page<MerchantHouse>> getHouseList(
            @Parameter(description = "当前页码", example = "1") 
            @RequestParam(defaultValue = "1") Integer currentPage,
            
            @Parameter(description = "每页数量", example = "10") 
            @RequestParam(defaultValue = "10") Integer pageSize,
            
            @Parameter(description = "房源名称", example = "海景房") 
            @RequestParam(required = false) String name,
            
            @Parameter(description = "房源类型：APARTMENT-公寓，VILLA-别墅等", example = "APARTMENT")
            @RequestParam(required = false) String type,
            
            @Parameter(description = "状态：0-下架，1-上架", example = "1") 
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(houseService.getHouseList(currentPage, pageSize, name, type, status));
    }

    @Operation(summary = "创建房源", 
              description = "创建新的房源信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping
    public Result<Void> createHouse(
        @Parameter(description = "房源信息", required = true) 
        @RequestBody MerchantHouseDTO houseDTO
    ) {

        // 验证房源分类
        if (!HouseCategoryEnum.contains(houseDTO.getCategory())) {
            log.info("houseDTO2:"+ houseDTO);
            throw new BusinessException("无效的房源分类，可选值：" +
                Arrays.stream(HouseCategoryEnum.values())
                    .map(category -> category.name() + "(" + category.getDescription() + ")")
                    .collect(Collectors.joining(", "))
            );
        }
        log.info("houseDTO:"+ houseDTO);
        houseService.createHouse(houseDTO);
        return Result.success();
    }

    @Operation(summary = "更新房源", 
              description = "更新指定房源的信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "房源不存在")
    })
    @PutMapping("/{id}")
    public Result<Void> updateHouse(
            @Parameter(description = "房源ID", required = true, example = "1") 
            @PathVariable Long id,
            
            @Parameter(description = "房源更新信息", required = true) 
            @RequestBody MerchantHouseDTO houseDTO
    ) {
        houseService.updateHouse(id, houseDTO);
        return Result.success();
    }

    @Operation(summary = "删除房源", 
              description = "删除指定的房源")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "删除成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "房源不存在")
    })
    @DeleteMapping("/{id}")
    public Result<Void> deleteHouse(
        @Parameter(description = "房源ID", required = true, example = "1") 
        @PathVariable Long id
    ) {
        houseService.deleteHouse(id);
        return Result.success();
    }

    @Operation(summary = "上传房源图片", 
              description = "上传房源的图片文件，支持jpg、png格式")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "上传成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "/uploads/house/xxx.jpg"))),
        @ApiResponse(responseCode = "400", description = "文件格式或大小不符合要求"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping("/upload")
    public Result<String> uploadHouseImage(
        @Parameter(description = "图片文件，支持jpg、png格式，大小不超过5MB", required = true) 
        @RequestParam("file") MultipartFile file
    ) {
        return Result.success(houseService.uploadHouseImage(file));
    }
} 