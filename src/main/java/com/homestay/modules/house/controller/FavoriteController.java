package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "收藏管理", description = "收藏相关接口")
@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "获取收藏列表")
    @GetMapping
    public Result<HouseListDTO> getFavoriteList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(favoriteService.getFavoriteList(page, pageSize));
    }
} 