package com.homestay.modules.merchant.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.MerchantProfileDTO;
import com.homestay.modules.merchant.dto.UpdatePasswordDTO;
import com.homestay.modules.merchant.entity.Merchant;
import com.homestay.modules.merchant.service.MerchantProfileService;
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
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "商家后台-商家信息", description = "提供商家个人信息的管理功能")
@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class MerchantProfileController {

    private final MerchantProfileService profileService;

    @Operation(summary = "获取商家信息", 
              description = "获取当前登录商家的详细信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Merchant.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "商家信息不存在")
    })
    @GetMapping("/profile")
    public Result<Merchant> getProfile() {
        return Result.success(profileService.getProfile());
    }

    @Operation(summary = "更新商家信息", 
              description = "更新商家的基本信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "商家信息不存在")
    })
    @PostMapping("/profile")
    public Result<Void> updateProfile(
        @Parameter(description = "商家信息", required = true)
        @RequestBody MerchantProfileDTO profileDTO
    ) {
        profileService.updateProfile(profileDTO);
        return Result.success();
    }

    @Operation(summary = "修改密码", 
              description = "修改商家登录密码")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录或原密码错误"),
        @ApiResponse(responseCode = "404", description = "商家信息不存在")
    })
    @PutMapping("/password")
    public Result<Void> updatePassword(
        @Parameter(description = "密码信息", required = true) 
        @Valid @RequestBody UpdatePasswordDTO passwordDTO
    ) {
        profileService.updatePassword(passwordDTO);
        return Result.success();
    }

    @Operation(summary = "上传头像", 
              description = "上传商家头像图片")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "上传成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "/uploads/avatar/xxx.jpg"))),
        @ApiResponse(responseCode = "400", description = "文件格式或大小不符合要求"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "商家信息不存在")
    })
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(
        @Parameter(description = "头像文件，支持jpg、png格式，大小不超过2MB", required = true) 
        @RequestParam("avatar") MultipartFile file
    ) {
        return Result.success(profileService.uploadAvatar(file));
    }

    @Operation(summary = "获取菜单列表", 
              description = "获取商家后台的菜单列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "array", 
                    example = "[{\"id\":1,\"name\":\"仪表盘\",\"icon\":\"dashboard\",\"path\":\"/merchant/dashboard\"}]"))),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @GetMapping("/menus")
    public Result<List<Map<String, Object>>> getMenus() {
        return Result.success(profileService.getMenus());
    }

    @Operation(summary = "获取商家信息页面", 
              description = "获取商家信息页面的路由")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "merchant/info"))),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @GetMapping("/info")
    public Result<String> getMerchantInfo() {
        return Result.success("merchant/info");
    }
} 