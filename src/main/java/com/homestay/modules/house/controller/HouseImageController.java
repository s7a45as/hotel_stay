package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.service.HouseImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "房源图片管理", description = "房源图片上传、删除等接口")
@RestController
@RequestMapping("/houses/images")
@RequiredArgsConstructor
public class HouseImageController {

    private final HouseImageService houseImageService;

    @Operation(summary = "上传房源图片")
    @PostMapping("/{houseId}")
    public Result<String> uploadImage(
            @PathVariable Long houseId,
            @RequestParam("file") MultipartFile file) {
        return Result.success(houseImageService.uploadImage(houseId, file));
    }

    @Operation(summary = "删除房源图片")
    @DeleteMapping("/{houseId}")
    public Result<Void> deleteImage(
            @PathVariable Long houseId,
            @RequestParam String imageUrl) {
        houseImageService.deleteImage(houseId, imageUrl);
        return Result.success();
    }

    @Operation(summary = "获取房源图片列表")
    @GetMapping("/{houseId}")
    public Result<List<String>> getImageList(@PathVariable Long houseId) {
        return Result.success(houseImageService.getImageList(houseId));
    }

    @Operation(summary = "设置房源主图")
    @PutMapping("/{houseId}/main")
    public Result<Void> setMainImage(
            @PathVariable Long houseId,
            @RequestParam String imageUrl) {
        houseImageService.setMainImage(houseId, imageUrl);
        return Result.success();
    }
} 