package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserSettingsDTO;
import com.homestay.modules.user.service.UserSettingsService;
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

@Tag(name = "用户-设置", description = "提供用户系统设置的查询和更新功能")
@RestController
@RequestMapping("/user/settings")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    @Operation(summary = "获取用户设置", 
              description = "获取当前用户的系统设置，包括通知设置和偏好设置")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserSettingsDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "设置信息不存在")
    })
    @GetMapping
    public Result<UserSettingsDTO> getUserSettings() {
        return Result.success(userSettingsService.getUserSettings());
    }

    @Operation(summary = "更新用户设置", 
              description = "更新当前用户的系统设置")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @PutMapping
    public Result<Void> updateUserSettings(
        @Parameter(description = "用户设置信息", required = true)
        @RequestBody UserSettingsDTO settingsDTO
    ) {
        userSettingsService.updateUserSettings(settingsDTO);
        return Result.success();
    }
} 