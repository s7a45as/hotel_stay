package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.dto.UpdatePasswordDTO;
import com.homestay.modules.admin.dto.UpdateUserDTO;
import com.homestay.modules.admin.dto.UserPageDTO;
import com.homestay.modules.admin.service.AdminUserService;
import com.homestay.modules.admin.vo.AdminUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理后台-用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public Result<UserPageDTO> getUserList(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer currentPage,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "用户名") @RequestParam(required = false) String username,
            @Parameter(description = "手机号") @RequestParam(required = false) String phone,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        return Result.success(adminUserService.getUserList(currentPage, pageSize, username, phone, status));
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}/update")
    public Result<Void> updateUser(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        adminUserService.updateUser(id, updateUserDTO);
        return Result.success();
    }

    @Operation(summary = "修改用户密码")
    @PutMapping("/{id}/password")
    public Result<Void> updateUserPassword(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Valid @RequestBody UpdatePasswordDTO passwordDTO) {
        adminUserService.updateUserPassword(id, passwordDTO);
        return Result.success();
    }

    @Operation(summary = "更新用户状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateUserStatus(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestBody Integer status) {
        adminUserService.updateUserStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "审核管理员")
    @PutMapping("/{id}/audit")
    public Result<Void> auditAdmin(
            @Parameter(description = "管理员ID") @PathVariable Long id,
            @Valid @RequestBody AdminAuditDTO auditDTO) {
        adminUserService.auditAdmin(id, auditDTO);
        return Result.success();
    }

    @Operation(summary = "获取待审核管理员列表")
    @GetMapping("/pending")
    public Result<List<AdminUserVO>> getPendingAdmins() {
        return Result.success(adminUserService.getPendingAdmins());
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        adminUserService.deleteUser(id);
        return Result.success();
    }
} 