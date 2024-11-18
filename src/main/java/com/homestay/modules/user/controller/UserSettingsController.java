package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserSettingsDTO;
import com.homestay.modules.user.service.UserSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户-设置", description = "用户设置相关接口")
@RestController
@RequestMapping("/user/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    @Operation(summary = "获取用户设置")
    @GetMapping
    public Result<UserSettingsDTO> getUserSettings() {
        return Result.success(userSettingsService.getUserSettings());
    }

    @Operation(summary = "更新用户设置")
    @PutMapping
    public Result<Void> updateUserSettings(@RequestBody UserSettingsDTO settingsDTO) {
        userSettingsService.updateUserSettings(settingsDTO);
        return Result.success();
    }
} 