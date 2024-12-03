package com.homestay.modules.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.MerchantAuditDTO;
import com.homestay.modules.admin.entity.SysMerchant;
import com.homestay.modules.admin.service.SysMerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商家管理")
@RestController
@RequestMapping("/admin/merchants")
@RequiredArgsConstructor
public class SysMerchantController {

    private final SysMerchantService merchantService;

    @Operation(summary = "获取商家列表")
    @GetMapping
    public Result<Page<SysMerchant>> getMerchantList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return Result.success(merchantService.getMerchantList(page, pageSize, keyword, 
                                                            status, startDate, endDate));
    }

    @Operation(summary = "审核商家")
    @PostMapping("/{id}/audit")
    public Result<Void> auditMerchant(@PathVariable Long id, 
                                     @Valid @RequestBody MerchantAuditDTO auditDTO) {
        merchantService.auditMerchant(id, auditDTO);
        return Result.success();
    }

    @Operation(summary = "更新商家状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateMerchantStatus(@PathVariable Long id, 
                                            @RequestParam String status) {
        merchantService.updateMerchantStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "删除商家")
    @DeleteMapping("/{id}")
    public Result<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return Result.success();
    }
} 