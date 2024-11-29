package com.homestay.modules.comUtils.controller;

import com.homestay.modules.comUtils.service.ComUtilsService;
import com.homestay.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
@Tag(name = "公共工具接口", description = "提供城市、地区等公共数据接口")
public class ComUtilsController {

    private final ComUtilsService comUtilsService;

    @Operation(summary = "获取城市列表")
    @GetMapping("/cities")
    public Result<List<Map<String, String>>> getCityList() {
        try {
            List<Map<String, String>> cities = comUtilsService.getCityList();
            return Result.success(cities);
        } catch (Exception e) {
            log.error("获取城市列表失败", e);
            return Result.error("获取城市列表失败");
        }
    }

    @Operation(summary = "获取地区列表")
    @GetMapping("/districts")
    public Result<List<Map<String, String>>> getDistrictList(
        @Parameter(description = "城市编码", required = true)
        @RequestParam String cityCode
    ) {
        try {
            List<Map<String, String>> districts = comUtilsService.getDistrictList(cityCode);
            return Result.success(districts);
        } catch (Exception e) {
            log.error("获取地区列表失败", e);
            return Result.error("获取地区列表失败");
        }
    }
} 