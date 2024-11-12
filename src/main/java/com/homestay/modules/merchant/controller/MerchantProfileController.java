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

@Tag(name = "商家后台-商家信息", description = "商家信息管理相关接口")
@RestController
@RequestMapping("/api/merchant")
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
} 