package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.dto.PromotionUsageRequest;
import com.homestay.modules.house.entity.PromotionUsage;
import com.homestay.modules.house.service.PromotionUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "优惠使用记录", description = "优惠活动使用记录相关接口")
@RestController
@RequestMapping("/house/promotions/usage")
@RequiredArgsConstructor
@Slf4j
public class PromotionUsageController {

    private final PromotionUsageService promotionUsageService;

    @Operation(summary = "记录优惠使用情况")
    @PostMapping("/record")
    public Result<Void> recordUsage(
            @Parameter(description = "优惠使用记录") @RequestBody PromotionUsageRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        log.info("记录优惠使用情况: {}", request);
        promotionUsageService.recordUsage(request, userId);
        return Result.success();
    }
} 