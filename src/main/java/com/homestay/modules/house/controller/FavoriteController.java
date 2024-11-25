package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.dto.HouseListDTO;
import com.homestay.modules.house.service.FavoriteService;
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

@Tag(name = "收藏管理", description = "提供房源收藏相关功能，包括收藏列表查询等")
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "获取收藏列表", 
              description = "分页获取当前用户收藏的房源列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = HouseListDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping
    public Result<HouseListDTO> getFavoriteList(
            @Parameter(description = "页码，从1开始", example = "1")
            @RequestParam(defaultValue = "1") Integer page,
            
            @Parameter(description = "每页数量，最大50", example = "10") 
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return Result.success(favoriteService.getFavoriteList(page, pageSize));
    }
} 