package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.SystemSettingsDTO;
import com.homestay.modules.admin.service.AdminSettingsService;
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

@Tag(name = "管理后台-系统设置", description = "提供系统配置的查询和更新功能，需要管理员权限")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class AdminSettingsController {

    private final AdminSettingsService adminSettingsService;

    @Operation(summary = "获取系统设置", 
              description = "获取当前系统的所有配置信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SystemSettingsDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/settings")
    public Result<SystemSettingsDTO> getSettings() {
        return Result.success(adminSettingsService.getSettings());
    }

    @Operation(summary = "更新系统设置", 
              description = "更新系统配置信息，部分配置项更新后需要重启服务才能生效")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PutMapping("/settings")
    public Result<Void> updateSettings(
        @Parameter(description = "系统设置信息", required = true)
        @RequestBody SystemSettingsDTO settings
    ) {
        adminSettingsService.updateSettings(settings);
        return Result.success();
    }

    @Operation(summary = "上传文件", 
              description = "上传系统相关的文件，如logo、favicon等")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "上传成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "/uploads/logo.png"))),
        @ApiResponse(responseCode = "400", description = "文件格式或大小不符合要求"),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping("/upload")
    public Result<String> uploadFile(
        @Parameter(description = "上传的文件，支持jpg、png、gif格式，大小不超过2MB",
                  required = true)
        @RequestParam("file") MultipartFile file
    ) {
        String fileUrl = adminSettingsService.uploadFile(file);
        return Result.success(fileUrl);
    }
} 