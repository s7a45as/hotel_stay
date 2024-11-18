package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserPrivacyDTO;
import com.homestay.modules.user.service.UserPrivacyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户-隐私设置", description = "用户隐私设置相关接口")
@RestController
@RequestMapping("/user/privacy")
@RequiredArgsConstructor
public class UserPrivacyController {

    private final UserPrivacyService userPrivacyService;

    @Operation(summary = "获取隐私设置")
    @GetMapping
    public Result<UserPrivacyDTO> getPrivacySettings() {
        return Result.success(userPrivacyService.getPrivacySettings());
    }

    @Operation(summary = "更新隐私设置")
    @PutMapping
    public Result<Void> updatePrivacySettings(@RequestBody UserPrivacyDTO privacyDTO) {
        userPrivacyService.updatePrivacySettings(privacyDTO);
        return Result.success();
    }
} 