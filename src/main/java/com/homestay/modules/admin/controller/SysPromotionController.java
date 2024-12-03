package com.homestay.modules.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.SysPromotionDTO;
import com.homestay.modules.admin.entity.SysPromotion;
import com.homestay.modules.admin.service.SysPromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "优惠活动管理")
@RestController
@RequestMapping("/admin/promotions")
@RequiredArgsConstructor
public class SysPromotionController {

    private final SysPromotionService promotionService;

    @Operation(summary = "获取优惠活动列表")
    @GetMapping
    public Result<Page<SysPromotion>> getPromotionList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return Result.success(promotionService.getPromotionList(page, pageSize, keyword, 
                                                              status, startDate, endDate));
    }

    @Operation(summary = "创建优惠活动")
    @PostMapping
    public Result<Void> createPromotion(@Valid @RequestBody SysPromotionDTO promotionDTO) {
        promotionService.createPromotion(promotionDTO);
        return Result.success();
    }

    @Operation(summary = "更新优惠活动")
    @PutMapping("/{id}")
    public Result<Void> updatePromotion(@PathVariable Long id, 
                                       @Valid @RequestBody SysPromotionDTO promotionDTO) {
        promotionService.updatePromotion(id, promotionDTO);
        return Result.success();
    }

    @Operation(summary = "删除优惠活动")
    @DeleteMapping("/{id}")
    public Result<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return Result.success();
    }
} 