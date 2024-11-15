package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.SystemSettingsDTO;
import com.homestay.modules.admin.service.AdminSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台-系统设置", description = "系统设置相关接口")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminSettingsController {

    private final AdminSettingsService adminSettingsService;

    @Operation(summary = "获取系统设置")
    @GetMapping("/settings")
    public Result<SystemSettingsDTO> getSettings() {
        return Result.success(adminSettingsService.getSettings());
    }

    @Operation(summary = "更新系统设置")
    @PutMapping("/settings")
    public Result<Void> updateSettings(@RequestBody SystemSettingsDTO settings) {
        adminSettingsService.updateSettings(settings);
        return Result.success();
    }

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = adminSettingsService.uploadFile(file);
        return Result.success(fileUrl);
    }
} 