package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.service.HouseImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "房源图片管理", description = "提供房源图片的上传、删除、查询等功能")
@RestController
@RequestMapping("/houses/images")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class HouseImageController {

    private final HouseImageService houseImageService;

    @Operation(summary = "上传房源图片", 
              description = "上传房源的图片文件，支持jpg、png格式")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "上传成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "/uploads/house/xxx.jpg"))),
        @ApiResponse(responseCode = "400", description = "文件格式或大小不符合要求"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "房源不存在")
    })
    @PostMapping("/{houseId}")
    public Result<String> uploadImage(
            @Parameter(description = "房源ID", required = true, example = "1")
            @PathVariable Long houseId,
            
            @Parameter(description = "图片文件，支持jpg、png格式，大小不超过5MB", required = true) 
            @RequestParam("file") MultipartFile file
    ) {
        return Result.success(houseImageService.uploadImage(houseId, file));
    }

    @Operation(summary = "删除房源图片", 
              description = "删除指定房源的图片")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "删除成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "房源或图片不存在")
    })
    @DeleteMapping("/{houseId}")
    public Result<Void> deleteImage(
            @Parameter(description = "房源ID", required = true, example = "1") 
            @PathVariable Long houseId,
            
            @Parameter(description = "图片URL", required = true, 
                      example = "/uploads/house/xxx.jpg") 
            @RequestParam String imageUrl
    ) {
        houseImageService.deleteImage(houseId, imageUrl);
        return Result.success();
    }

    @Operation(summary = "获取房源图片列表", 
              description = "获取指定房源的所有图片列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", 
                    example = "[\"/uploads/house/xxx.jpg\"]"))),
        @ApiResponse(responseCode = "404", description = "房源不存在")
    })
    @GetMapping("/{houseId}")
    public Result<List<String>> getImageList(
        @Parameter(description = "房源ID", required = true, example = "1") 
        @PathVariable Long houseId
    ) {
        return Result.success(houseImageService.getImageList(houseId));
    }

    @Operation(summary = "设置房源主图", 
              description = "设置指定图片为房源的主图")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "设置成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "房源或图片不存在")
    })
    @PutMapping("/{houseId}/main")
    public Result<Void> setMainImage(
            @Parameter(description = "房源ID", required = true, example = "1") 
            @PathVariable Long houseId,
            
            @Parameter(description = "图片URL", required = true, 
                      example = "/uploads/house/xxx.jpg") 
            @RequestParam String imageUrl
    ) {
        houseImageService.setMainImage(houseId, imageUrl);
        return Result.success();
    }
} 