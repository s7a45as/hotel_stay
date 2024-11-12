package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserProfileDTO;
import com.homestay.modules.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "用户-个人信息", description = "用户个人信息相关接口")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "获取用户个人信息")
    @GetMapping("/profile")
    public Result<UserProfileDTO> getUserProfile() {
        return Result.success(userProfileService.getUserProfile());
    }

    @Operation(summary = "更新用户个人信息")
    @PutMapping("/profile")
    public Result<Void> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        userProfileService.updateUserProfile(userProfileDTO);
        return Result.success();
    }

    @Operation(summary = "更新用户密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userProfileService.updatePassword(oldPassword, newPassword);
        return Result.success();
    }

    @Operation(summary = "上传用户头像")
    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String avatarUrl = userProfileService.uploadAvatar(file);
        return Result.success(avatarUrl);
    }
} 