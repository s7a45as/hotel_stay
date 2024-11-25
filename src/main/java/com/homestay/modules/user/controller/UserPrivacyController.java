package com.homestay.modules.user.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserPrivacyDTO;
import com.homestay.modules.user.service.UserPrivacyService;
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

@Tag(name = "用户-隐私设置", description = "提供用户隐私设置的查询和更新功能")
@RestController
@RequestMapping("/user/privacy")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class UserPrivacyController {

    private final UserPrivacyService userPrivacyService;

    @Operation(summary = "获取隐私设置", 
              description = "获取当前用户的隐私设置信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserPrivacyDTO.class))),
        @ApiResponse(responseCode = "401", description = "未登录"),
        @ApiResponse(responseCode = "404", description = "隐私设置不存在")
    })
    @GetMapping
    public Result<UserPrivacyDTO> getPrivacySettings() {
        return Result.success(userPrivacyService.getPrivacySettings());
    }

    @Operation(summary = "更新隐私设置", 
              description = "更新当前用户的隐私设置信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "未登录")
    })
    @PutMapping
    public Result<Void> updatePrivacySettings(
        @Parameter(description = "隐私设置信息", required = true)
        @RequestBody UserPrivacyDTO privacyDTO
    ) {
        userPrivacyService.updatePrivacySettings(privacyDTO);
        return Result.success();
    }
} 