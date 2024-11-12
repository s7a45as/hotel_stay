package com.homestay.modules.merchant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.response.Result;
import com.homestay.modules.merchant.entity.MerchantHouse;
import com.homestay.modules.merchant.dto.MerchantHouseDTO;
import com.homestay.modules.merchant.service.MerchantHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "商家后台-房源管理", description = "商家房源管理相关接口")
@RestController
@RequestMapping("/api/merchant/houses")
@RequiredArgsConstructor
public class MerchantHouseController {

    private final MerchantHouseService houseService;

    @Operation(summary = "获取房源列表")
    @GetMapping
    public Result<Page<MerchantHouse>> getHouseList(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(houseService.getHouseList(currentPage, pageSize, name, type, status));
    }

    @Operation(summary = "创建房源")
    @PostMapping
    public Result<Void> createHouse(@RequestBody MerchantHouseDTO houseDTO) {
        houseService.createHouse(houseDTO);
        return Result.success();
    }

    @Operation(summary = "更新房源")
    @PutMapping("/{id}")
    public Result<Void> updateHouse(@PathVariable Long id, @RequestBody MerchantHouseDTO houseDTO) {
        houseService.updateHouse(id, houseDTO);
        return Result.success();
    }

    @Operation(summary = "删除房源")
    @DeleteMapping("/{id}")
    public Result<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return Result.success();
    }

    @Operation(summary = "上传房源图片")
    @PostMapping("/upload")
    public Result<String> uploadHouseImage(@RequestParam("file") MultipartFile file) {
        return Result.success(houseService.uploadHouseImage(file));
    }
} 