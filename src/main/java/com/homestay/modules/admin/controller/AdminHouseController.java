package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.common.shareentity.House;
import com.homestay.modules.admin.dto.HousePageDTO;
import com.homestay.modules.admin.service.AdminHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理后台-房源管理", description = "房源管理相关接口")
@RestController
@RequestMapping("/admin/houses")
@RequiredArgsConstructor
public class AdminHouseController {

    private final AdminHouseService adminHouseService;

    @Operation(summary = "获取房源列表")
    @GetMapping
    public Result<HousePageDTO> getHouseList(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminHouseService.getHouseList(currentPage, pageSize, name, type, status));
    }

    @Operation(summary = "更新房源信息")
    @PutMapping("/{id}")
    public Result<Void> updateHouse(@PathVariable Long id, @RequestBody House house) {
        adminHouseService.updateHouse(id, house);
        return Result.success();
    }

    @Operation(summary = "删除房源")
    @DeleteMapping("/{id}")
    public Result<Void> deleteHouse(@PathVariable Long id) {
        adminHouseService.deleteHouse(id);
        return Result.success();
    }
} 