package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.MerchantProfileDTO;
import com.homestay.modules.merchant.dto.UpdatePasswordDTO;
import com.homestay.modules.merchant.entity.Merchant;
import com.homestay.modules.merchant.service.MerchantProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "商家后台-商家信息", description = "商家信息管理相关接口")
@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor
public class MerchantProfileController {

    private final MerchantProfileService profileService;

    @Operation(summary = "获取商家信息")
    @GetMapping("/profile")
    public Result<Merchant> getProfile() {
        return Result.success(profileService.getProfile());
    }

    @Operation(summary = "更新商家信息")
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody MerchantProfileDTO profileDTO) {
        profileService.updateProfile(profileDTO);
        return Result.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(@Valid @RequestBody UpdatePasswordDTO passwordDTO) {
        profileService.updatePassword(passwordDTO);
        return Result.success();
    }

    @Operation(summary = "上传头像")
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return Result.success(profileService.uploadAvatar(file));
    }

    @Operation(summary = "获取菜单列表")
    @GetMapping("/menus")
    public Result<List<Map<String, Object>>> getMenus() {
        List<Map<String, Object>> menus = new ArrayList<>();
        
        // 仪表盘
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("id", 1);
        dashboard.put("name", "仪表盘");
        dashboard.put("icon", "dashboard");
        dashboard.put("path", "/merchant/dashboard");
        menus.add(dashboard);
        
        // 房源管理
        Map<String, Object> house = new HashMap<>();
        house.put("id", 2);
        house.put("name", "房源管理");
        house.put("icon", "house"); 
        house.put("path", "/merchant/houses");
        menus.add(house);
        
        // 订单管理
        Map<String, Object> order = new HashMap<>();
        order.put("id", 3);
        order.put("name", "订单管理");
        order.put("icon", "order");
        order.put("path", "/merchant/orders");
        menus.add(order);
        
        // 个人信息
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", 4);
        profile.put("name", "个人信息");
        profile.put("icon", "user");
        profile.put("path", "/merchant/info");
        menus.add(profile);
        
        return Result.success(menus);
    }

    @Operation(summary = "获取商家信息页面")
    @GetMapping("/info")
    public Result<String> getMerchantInfo() {
        return Result.success("merchant/info");
    }
} 