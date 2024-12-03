package com.homestay.modules.house.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.house.entity.PriceCalculationResult;
import com.homestay.modules.house.service.PriceCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Tag(name = "价格计算", description = "房源价格计算相关接口")
@RestController
@RequestMapping("/house/price")
@RequiredArgsConstructor
@Slf4j
public class PriceCalculationController {

    private final PriceCalculationService priceCalculationService;

    @Operation(summary = "计算最终价格", description = "计算房源预订的最终价格，包含可用优惠")
    @PostMapping("/calculate")
    public Result<PriceCalculationResult> calculatePrice(
            @Parameter(description = "房源ID") @RequestParam Long houseId,
            @Parameter(description = "原始价格") @RequestParam BigDecimal originalPrice,
            @Parameter(description = "入住时间") @RequestParam LocalDateTime checkInTime,
            @Parameter(description = "退房时间") @RequestParam LocalDateTime checkOutTime,
            @Parameter(description = "订单ID(可选)") @RequestParam(required = false) String orderId,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        log.debug("计算后的总价格:{}",priceCalculationService.calculatePrice(
                houseId, originalPrice, checkInTime, checkOutTime, orderId, userId));
        return Result.success(priceCalculationService.calculatePrice(
                houseId, originalPrice, checkInTime, checkOutTime, orderId, userId));
    }
} 