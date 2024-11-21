package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserProfileDTO;
import com.homestay.modules.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "用户-个人信息", description = "用户个人信息相关接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "获取用户个人信息")
    @GetMapping("/profile")
    public Result<UserProfileDTO> getUserProfile() {
        return Result.success(userProfileService.getUserProfile());
    }

    @Operation(summary = "更新用户个人信息")
    @PostMapping("/profile/update")
    public Result<Void> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        log.info(String.valueOf(userProfileDTO));
        userProfileService.updateUserProfile(userProfileDTO);
        return Result.success();
    }


    @Operation(summary = "上传用户头像")
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String avatarUrl = userProfileService.uploadAvatar(file);
        return Result.success(avatarUrl);
    }

    @Operation(summary = "获取菜单列表")
    @GetMapping("/menus")
    public Result<List<Map<String, Object>>> getMenus() {
        List<Map<String, Object>> menus = new ArrayList<>();
        
        // 首页
        Map<String, Object> home = new HashMap<>();
        home.put("id", 1);
        home.put("name", "首页");
        home.put("icon", "home");
        home.put("path", "/user/home");
        menus.add(home);
        
        // 订单管理
        Map<String, Object> order = new HashMap<>();
        order.put("id", 2);
        order.put("name", "我的订单");
        order.put("icon", "order");
        order.put("path", "/user/orders");
        menus.add(order);
        
        // 收藏夹
        Map<String, Object> favorite = new HashMap<>();
        favorite.put("id", 3);
        favorite.put("name", "我的收藏");
        favorite.put("icon", "star");
        favorite.put("path", "/user/favorites");
        menus.add(favorite);
        
        // 个人信息
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", 4);
        profile.put("name", "个人信息");
        profile.put("icon", "user");
        profile.put("path", "/user/info");
        menus.add(profile);
        
        // 隐私设置
        Map<String, Object> privacy = new HashMap<>();
        privacy.put("id", 5);
        privacy.put("name", "隐私设置");
        privacy.put("icon", "lock");
        privacy.put("path", "/user/privacy");
        menus.add(privacy);
        
        // 系统设置
        Map<String, Object> settings = new HashMap<>();
        settings.put("id", 6);
        settings.put("name", "系统设置");
        settings.put("icon", "setting");
        settings.put("path", "/user/settings");
        menus.add(settings);
        
        return Result.success(menus);
    }

    @Operation(summary = "获取用户信息页面")
    @GetMapping("/info")
    public Result<String> getUserInfo() {
        return Result.success("user/info");
    }
} 