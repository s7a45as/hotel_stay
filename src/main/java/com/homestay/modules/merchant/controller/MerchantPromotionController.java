package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.merchant.dto.MerchantPromotionDTO;
import com.homestay.modules.merchant.service.MerchantPromotionService;
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

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "商家后台-优惠活动", description = "提供商家优惠活动的管理功能")
@RestController
@RequestMapping("/merchant/promotions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class MerchantPromotionController {

    private final MerchantPromotionService promotionService;

    @Operation(summary = "获取优惠活动列表", 
              description = "获取商家的所有优惠活动")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MerchantPromotion.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping
    public Result<List<MerchantPromotion>> getPromotionList() {
        return Result.success(promotionService.getPromotionList());
    }

    @Operation(summary = "创建优惠活动", 
              description = "创建新的优惠活动")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping
    public Result<Void> createPromotion(
        @Parameter(description = "优惠活动信息", required = true)
        @Valid @RequestBody MerchantPromotionDTO promotionDTO
    ) {
        promotionService.createPromotion(promotionDTO);
        return Result.success();
    }

    @Operation(summary = "更新优惠活动", 
              description = "更新指定的优惠活动")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    @PutMapping("/{id}")
    public Result<Void> updatePromotion(
        @Parameter(description = "活动ID", required = true, example = "1")
        @PathVariable Long id,
        
        @Parameter(description = "优惠活动信息", required = true)
        @Valid @RequestBody MerchantPromotionDTO promotionDTO
    ) {
        promotionService.updatePromotion(id, promotionDTO);
        return Result.success();
    }

    @Operation(summary = "删除优惠活动", 
              description = "删除指定的优惠活动")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "删除成功"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问"),
        @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    @DeleteMapping("/{id}")
    public Result<Void> deletePromotion(
        @Parameter(description = "活动ID", required = true, example = "1")
        @PathVariable Long id
    ) {
        promotionService.deletePromotion(id);
        return Result.success();
    }
} 