package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.HouseCreateDTO;
import com.homestay.modules.admin.dto.HousePageDTO;
import com.homestay.modules.admin.dto.HouseUpdateDTO;
import com.homestay.modules.admin.service.AdminHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@Tag(name = "管理后台-房源管理", description = "提供房源的增删改查、导出等功能，需要管理员权限")
@RestController
@RequestMapping("/admin/houses")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class AdminHouseController {

    private final AdminHouseService adminHouseService;

    @Operation(summary = "获取房源列表", 
              description = "分页获取房源列表，支持按名称、类型和状态筛选")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HousePageDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping
    public Result<HousePageDTO> getHouseList(
            @Parameter(description = "当前页码", example = "1") 
            @RequestParam(defaultValue = "1") Integer currentPage,
            
            @Parameter(description = "每页数量", example = "10") 
            @RequestParam(defaultValue = "10") Integer pageSize,
            
            @Parameter(description = "房源名称，支持模糊搜索", example = "海景房") 
            @RequestParam(required = false) String name,
            
            @Parameter(description = "房源类型：APARTMENT-公寓，VILLA-别墅等", example = "APARTMENT") 
            @RequestParam(required = false) String type,
            
            @Parameter(description = "状态：0-下架，1-上架，2-预订中", example = "1") 
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(adminHouseService.getHouseList(currentPage, pageSize, name, type, status));
    }

    @Operation(summary = "创建房源", description = "创建新的房源信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping
    public Result<Void> createHouse(
        @Parameter(description = "房源创建信息", required = true)
        @Valid @RequestBody HouseCreateDTO house
    ) {
        adminHouseService.createHouse(house);
        return Result.success();
    }

    @Operation(summary = "更新房源", description = "更新指定房源的信息")
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
            @Valid @RequestBody HouseUpdateDTO house
    ) {
        adminHouseService.updateHouse(id, house);
        return Result.success();
    }

    @Operation(summary = "删除房源", description = "删除指定的房源信息")
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
        adminHouseService.deleteHouse(id);
        return Result.success();
    }

    @Operation(summary = "导出房源数据", 
              description = "导出房源数据为Excel文件，支持筛选条件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "导出成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportHouses(
            @Parameter(description = "房源名称，支持模糊搜索", example = "海景房") 
            @RequestParam(required = false) String name,
            
            @Parameter(description = "房源类型：APARTMENT-公寓，VILLA-别墅等", example = "APARTMENT") 
            @RequestParam(required = false) String type,
            
            @Parameter(description = "状态：0-下架，1-上架，2-预订中", example = "1") 
            @RequestParam(required = false) Integer status
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