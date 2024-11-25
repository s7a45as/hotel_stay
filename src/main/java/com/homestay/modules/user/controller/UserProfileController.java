package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserProfileDTO;
import com.homestay.modules.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "用户-个人信息", description = "提供用户个人信息的查询和更新功能")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "获取用户个人信息", 
              description = "获取当前登录用户的详细信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserProfileDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    @GetMapping("/profile")
    public Result<UserProfileDTO> getUserProfile() {
        return Result.success(userProfileService.getUserProfile());
    }

    @Operation(summary = "更新用户个人信息", 
              description = "更新当前用户的个人信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    @PostMapping("/profile/update")
    public Result<Void> updateUserProfile(
        @Parameter(description = "用户个人信息", required = true)
        @RequestBody UserProfileDTO userProfileDTO
    ) {
        userProfileService.updateUserProfile(userProfileDTO);
        return Result.success();
    }

    @Operation(summary = "上传用户头像", 
              description = "上传并更新用户头像图片")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "上传成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "/uploads/avatar/xxx.jpg"))),
        @ApiResponse(responseCode = "400", description = "文件格式或大小不符合要求"),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @PostMapping("/avatar/upload")
    public Result<String> uploadAvatar(
        @Parameter(description = "头像文件，支持jpg、png格式，大小不超过2MB", required = true)
        @RequestParam("file") MultipartFile file
    ) {
        return Result.success(userProfileService.uploadAvatar(file));
    }

    @Operation(summary = "获取菜单列表", 
              description = "获取用户端的菜单列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", 
                    example = "[{\"id\":1,\"name\":\"首页\",\"icon\":\"home\",\"path\":\"/user/home\"}]"))),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @GetMapping("/menus")
    public Result<List<Map<String, Object>>> getMenus() {
        return Result.success(generateMenus());
    }

    @Operation(summary = "获取用户信息页面", 
              description = "获取用户信息页面的路由")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "user/info"))),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @GetMapping("/info")
    public Result<String> getUserInfo() {
        return Result.success("user/info");
    }

    // 生成菜单列表的辅助方法
    private List<Map<String, Object>> generateMenus() {
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
        
        return menus;
    }
} 