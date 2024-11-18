package com.homestay.modules.admin.controller;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.Result;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.service.AdminAuditService;
import com.homestay.modules.admin.vo.AdminUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
@Tag(name = "管理员用户管理", description = "管理员用户相关接口")
public class AdminAuditController {

    private final AdminAuditService adminAuditService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Operation(summary = "审核管理员注册")
    @PostMapping("/audit/admin/{id}")
    public Result<Void> auditAdmin(
        @PathVariable Long id,
        @RequestParam String token, 
        @RequestBody AdminAuditDTO auditDTO
    ) {
        // 设置ID
        auditDTO.setId(id);
        
        // 验证token
        String key = "audit:token:" + id;
        String storedToken = (String) redisTemplate.opsForValue().get(key);
        
        if (storedToken == null || !storedToken.equals(token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 设置审核结果
        auditDTO.setStatus(auditDTO.getApproved() ? 1 : 0);

        // 执行审核
        adminAuditService.auditAdmin(auditDTO);

        // 删除Redis中的token
        redisTemplate.delete(key);

        return Result.success();
    }

    @Operation(summary = "获取待审核管理员详情")
    @GetMapping("/audit/admin/{id}")
    public Result<AdminUserVO> getAdminAuditDetail(@PathVariable Long id) {
        return Result.success(adminAuditService.getAdminAuditDetail(id));
    }

    @Operation(summary = "审核商家注册")
    @PostMapping("/audit/merchant/{id}")
    public Result<Void> auditMerchant(
        @PathVariable Long id,
        @RequestParam String token, 
        @RequestBody AdminAuditDTO auditDTO
    ) {
        // 设置ID
        auditDTO.setId(id);
        
        // 验证token
        String key = "audit:token:" + id;
        String storedToken = (String) redisTemplate.opsForValue().get(key);
        
        if (storedToken == null || !storedToken.equals(token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 设置审核结果
        auditDTO.setStatus(auditDTO.getApproved() ? 1 : 0);

        // 执行审核
        adminAuditService.auditMerchant(auditDTO);

        // 删除Redis中的token
        redisTemplate.delete(key);

        return Result.success();
    }

    @Operation(summary = "获取待审核商家详情")
    @GetMapping("/audit/merchant/{id}")
    public String getMerchantAuditDetail(
        @PathVariable Long id,
        @RequestParam String token,
        Model model
    ) {
        // 1. 验证token
        String key = "audit:token:" + id;
        String storedToken = (String) redisTemplate.opsForValue().get(key);
        
        if (storedToken == null || !storedToken.equals(token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 2. 获取商家详情
        AdminUserVO merchantInfo = adminAuditService.getMerchantAuditDetail(id);
        
        // 3. 将数据添加到模型中
        model.addAttribute("merchant", merchantInfo);
        model.addAttribute("token", token);
        model.addAttribute("id", id);
        
        // 4. 返回审核页面模板
        return "admin/merchant-audit";
    }

} 