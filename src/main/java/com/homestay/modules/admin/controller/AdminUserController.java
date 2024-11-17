package com.homestay.modules.admin.controller;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.Result;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Tag(name = "管理员用户管理", description = "管理员用户相关接口")
public class AdminUserController {

    private final AdminUserService adminUserService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Operation(summary = "审核管理员注册")
    @PostMapping("/audit")
    public Result<Void> auditAdmin(@RequestParam String token, @RequestBody AdminAuditDTO auditDTO) {
        // 验证token
        String key = "audit:token:" + auditDTO.getId();
        String storedToken = (String) redisTemplate.opsForValue().get(key);
        
        if (storedToken == null || !storedToken.equals(token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 执行审核
        adminUserService.auditAdmin(auditDTO);

        // 删除Redis中的token
        redisTemplate.delete(key);

        return Result.success();
    }

    @Operation(summary = "审核商家注册")
    @PostMapping("/audit/merchant")
    public Result<Void> auditMerchant(@RequestParam String token, @RequestBody AdminAuditDTO auditDTO) {
        // 验证token
        String key = "audit:token:" + auditDTO.getId();
        String storedToken = (String) redisTemplate.opsForValue().get(key);
        
        if (storedToken == null || !storedToken.equals(token)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 设置审核结果
        auditDTO.setStatus(auditDTO.getApproved() ? 1 : 0);

        // 执行审核
        adminUserService.auditMerchant(auditDTO);

        // 删除Redis中的token
        redisTemplate.delete(key);

        return Result.success();
    }

    // ... 其他方法
} 