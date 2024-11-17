package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.constant.CommonConstants;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.dto.UpdatePasswordDTO;
import com.homestay.modules.admin.dto.UpdateUserDTO;
import com.homestay.modules.admin.dto.UserPageDTO;
import com.homestay.modules.admin.service.AdminUserService;
import com.homestay.modules.admin.vo.AdminUserVO;
import com.homestay.modules.auth.entity.*;
import com.homestay.modules.auth.mapper.AdminMapper;
import com.homestay.modules.auth.mapper.AuthMerchantMapper;
import com.homestay.modules.auth.mapper.NormalUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminMapper adminMapper;
    private final NormalUserMapper normalUserMapper;
    private final AuthMerchantMapper authMerchantMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditAdmin(AdminAuditDTO auditDTO) {
        AdminUser admin = adminMapper.selectById(auditDTO.getId());
        if (admin == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        // 设置审核状态
        admin.setStatus(auditDTO.getApproved() ? 1 : 2);
        admin.setRemark(auditDTO.getRemark());

        adminMapper.updateById(admin);
        log.info("管理员审核完成: {}, 结果: {}", admin.getUsername(), auditDTO.getApproved());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditMerchant(AdminAuditDTO auditDTO) {
        AuthMerchant merchant = authMerchantMapper.selectById(auditDTO.getId());
        if (merchant == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        // 设置审核状态
        merchant.setStatus(auditDTO.getApproved() ? 1 : 0);
        merchant.setAuditRemark(auditDTO.getRemark());

        authMerchantMapper.updateById(merchant);
        log.info("商家审核完成: {}, 结果: {}", merchant.getUsername(), auditDTO.getApproved());
    }

    @Override
    public List<AdminUserVO> getPendingAdmins() {
        // 查询待审核的管理员
        LambdaQueryWrapper<AdminUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUser::getStatus, 0);
        List<AdminUser> adminUsers = adminMapper.selectList(wrapper);
        
        // 转换为VO
        return adminUsers.stream()
                .map(adminUser -> {
                    AdminUserVO vo = new AdminUserVO();
                    BeanUtils.copyProperties(adminUser, vo);
                    return vo;
                })
                .toList();
    }

    @Override
    public UserPageDTO getUserList(Integer currentPage, Integer pageSize, String username, String phone, Integer status) {
        // 分别查询普通用户和商家
        List<BaseUser> allUsers = new ArrayList<>();
        long total = 0;

        // 查询普通用户
        Page<NormalUser> normalUserPage = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<NormalUser> normalUserWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            normalUserWrapper.like(NormalUser::getUsername, username);
        }
        if (StringUtils.hasText(phone)) {
            normalUserWrapper.like(NormalUser::getPhone, phone);
        }
        if (status != null) {
            normalUserWrapper.eq(NormalUser::getStatus, status);
        }
        Page<NormalUser> normalUsers = normalUserMapper.selectPage(normalUserPage, normalUserWrapper);
        allUsers.addAll(normalUsers.getRecords());
        total += normalUsers.getTotal();

        // 查询商家用户
        Page<AuthMerchant> merchantPage = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<AuthMerchant> merchantWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            merchantWrapper.like(AuthMerchant::getUsername, username);
        }
        if (StringUtils.hasText(phone)) {
            merchantWrapper.like(AuthMerchant::getPhone, phone);
        }
        if (status != null) {
            merchantWrapper.eq(AuthMerchant::getStatus, status);
        }
        Page<AuthMerchant> merchants = authMerchantMapper.selectPage(merchantPage, merchantWrapper);
        allUsers.addAll(merchants.getRecords());
        total += merchants.getTotal();

        return UserPageDTO.builder()
                .total(total)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .list(allUsers)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserStatus(Long id, Integer status) {
        // 先尝试在普通用户表中更新
        NormalUser normalUser = normalUserMapper.selectById(id);
        if (normalUser != null) {
            normalUser.setStatus(status);
            normalUserMapper.updateById(normalUser);
            return;
        }

        // 如果不是普通用户，尝试在商家表中更新
        AuthMerchant authMerchant = authMerchantMapper.selectById(id);
        if (authMerchant != null) {
            // 商家状态特殊处理
            validateMerchantStatus(status);
            authMerchant.setStatus(status);
            authMerchantMapper.updateById(authMerchant);
            return;
        }

        throw new BusinessException("用户不存在");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 先尝试删除普通用户
        int normalUserCount = normalUserMapper.deleteById(id);
        if (normalUserCount > 0) {
            return;
        }

        // 如果不是普通用户，尝试删除商家
        int merchantCount = authMerchantMapper.deleteById(id);
        if (merchantCount > 0) {
            return;
        }

        throw new BusinessException("用户不存在");
    }

    @Override
    public void updateUser(Long id, UpdateUserDTO updateUserDTO) {
        // 先尝试在普通用户表中更新
        NormalUser normalUser = normalUserMapper.selectById(id);
        if (normalUser != null) {
            BeanUtils.copyProperties(updateUserDTO, normalUser);
            normalUserMapper.updateById(normalUser);
            return;
        }

        // 如果不是普通用户，��试在商家表中更新
        AuthMerchant merchant = authMerchantMapper.selectById(id);
        if (merchant != null) {
            BeanUtils.copyProperties(updateUserDTO, merchant);
            authMerchantMapper.updateById(merchant);
            return;
        }

        throw new BusinessException("用户不存在");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserPassword(Long id, UpdatePasswordDTO passwordDTO) {
        // 验证两次密码是否一致
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }

        // 先尝试在普通用户表中更新
        NormalUser normalUser = normalUserMapper.selectById(id);
        if (normalUser != null) {
            normalUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            normalUserMapper.updateById(normalUser);
            return;
        }

        // 如果不是普通用户，尝试在商家表中更新
        AuthMerchant merchant = authMerchantMapper.selectById(id);
        if (merchant != null) {
            merchant.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            authMerchantMapper.updateById(merchant);
            return;
        }

        throw new BusinessException("用户不存在");
    }

    /**
     * 验证商家状态值是否合法
     */
    private void validateMerchantStatus(Integer status) {
        if (status != CommonConstants.System.NORMAL_STATUS &&
            status != CommonConstants.System.DISABLED_STATUS &&
            status != CommonConstants.System.PENDING_STATUS) {
            throw new BusinessException("无效的商家状态值");
        }
    }
} 