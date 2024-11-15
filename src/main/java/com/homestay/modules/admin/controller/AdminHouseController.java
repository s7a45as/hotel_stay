package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.HouseCreateDTO;
import com.homestay.modules.admin.dto.HousePageDTO;
import com.homestay.modules.admin.dto.HouseUpdateDTO;
import com.homestay.modules.admin.service.AdminHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "管理后台-房源管理", description = "房源管理相关接口")
@RestController
@RequestMapping("/admin/houses")
@RequiredArgsConstructor
public class AdminHouseController {

    private final AdminHouseService adminHouseService;

    @Operation(summary = "获取房源列表")
    @GetMapping
    public Result<HousePageDTO> getHouseList(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer currentPage,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "房源名称") @RequestParam(required = false) String name,
            @Parameter(description = "房源类型") @RequestParam(required = false) String type,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminHouseService.getHouseList(currentPage, pageSize, name, type, status));
    }

    @Operation(summary = "创建房源")
    @PostMapping
    public Result<Void> createHouse(@Valid @RequestBody HouseCreateDTO house) {
        adminHouseService.createHouse(house);
        return Result.success();
    }

    @Operation(summary = "更新房源")
    @PutMapping("/{id}")
    public Result<Void> updateHouse(
            @Parameter(description = "房源ID") @PathVariable Long id,
            @Valid @RequestBody HouseUpdateDTO house
    ) {
        adminHouseService.updateHouse(id, house);
        return Result.success();
    }

    @Operation(summary = "删除房源")
    @DeleteMapping("/{id}")
    public Result<Void> deleteHouse(@Parameter(description = "房源ID") @PathVariable Long id) {
        adminHouseService.deleteHouse(id);
        return Result.success();
    }

    @Operation(summary = "导出房源数据")
    @GetMapping("/export")
    //返回类型为字节数组
    public ResponseEntity<byte[]> exportHouses(
            @Parameter(description = "房源名称") @RequestParam(required = false) String name,
            @Parameter(description = "房源类型") @RequestParam(required = false) String type,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status
    ) {
        byte[] data = adminHouseService.exportHouses(name, type, status);
        String filename = String.format("房源列表_%s.xls", 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", 
            new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
} 