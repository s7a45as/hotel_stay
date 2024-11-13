package com.homestay.modules.admin.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.service.AdminUserService;
import com.homestay.modules.admin.vo.AdminUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理后台-管理员管理", description = "管理员管理相关接口")
@RestController
@RequestMapping("/api/admin/admins")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Operation(summary = "审核管理员")
    @PutMapping("/{id}/audit")
    public Result<Void> auditAdmin(@PathVariable Long id, @RequestBody AdminAuditDTO auditDTO) {
        adminUserService.auditAdmin(id, auditDTO);
        return Result.success();
    }

    @Operation(summary = "获取待审核管理员列表")
    @GetMapping("/pending")
    public Result<List<AdminUserVO>> getPendingAdmins() {
        return Result.success(adminUserService.getPendingAdmins());
    }
} 