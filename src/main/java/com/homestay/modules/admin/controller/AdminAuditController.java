package com.homestay.modules.admin.controller;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.Result;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.service.AdminAuditService;
import com.homestay.modules.admin.vo.AdminUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "管理员用户管理", description = "管理员用户相关接口，包含管理员和商家的审核功能")
public class AdminAuditController {

    private final AdminAuditService adminAuditService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Operation(summary = "审核管理员注册", description = "审核管理员的注册申请，可以选择通过或拒绝")
    @PostMapping("/audit/admin/{id}")
    public Result<Void> auditAdmin(
        @Parameter(description = "待审核的管理员ID", required = true, example = "1") 
        @PathVariable Long id,
        
        @Parameter(description = "审核令牌，用于防止重复审核", required = true, 
            example = "8f7d3b2e-9a4c-4f8d-b8e5-6c3a7d9f2b1a") 
        @RequestParam String token, 
        
        @Parameter(description = "审核信息，包含审核结果和备注", required = true) 
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

    @Operation(summary = "获取待审核管理员详情", description = "获取待审核管理员的详细信息")
    @GetMapping("/audit/admin/{id}")
    public Result<AdminUserVO> getAdminAuditDetail(
        @Parameter(description = "待审核的管理员ID", required = true, example = "1") 
        @PathVariable Long id
    ) {
        return Result.success(adminAuditService.getAdminAuditDetail(id));
    }

    @Operation(summary = "审核商家注册", description = "审核商家的注册申请，可以选择通过或拒绝")
    @PostMapping("/audit/merchant/{id}")
    public Result<Void> auditMerchant(
        @Parameter(description = "待审核的商家ID", required = true, example = "1") 
        @PathVariable Long id,
        
        @Parameter(description = "审核令牌，用于防止重复审核", required = true, 
            example = "8f7d3b2e-9a4c-4f8d-b8e5-6c3a7d9f2b1a") 
        @RequestParam String token, 
        
        @Parameter(description = "审核信息，包含审核结果和备注", required = true) 
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

    @Operation(summary = "获取待审核商家详情", description = "获取待审核商家的详细信息，并返回审核页面")
    @GetMapping("/audit/merchant/{id}")
    public String getMerchantAuditDetail(
        @Parameter(description = "待审核的商家ID", required = true, example = "1") 
        @PathVariable Long id,
        
        @Parameter(description = "审核令牌，用于防止重复审核", required = true, 
            example = "8f7d3b2e-9a4c-4f8d-b8e5-6c3a7d9f2b1a") 
        @RequestParam String token,
        
        @Parameter(description = "Spring MVC Model对象，用于传递数据到视图", hidden = true)
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