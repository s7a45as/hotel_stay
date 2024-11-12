package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.constant.CommonConstants;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.dto.UserPageDTO;
import com.homestay.modules.admin.service.AdminUserService;
import com.homestay.modules.auth.entity.AuthMerchant;
import com.homestay.modules.auth.entity.BaseUser;
import com.homestay.modules.auth.entity.NormalUser;
import com.homestay.modules.auth.mapper.AuthMerchantMapper;
import com.homestay.modules.auth.mapper.NormalUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final NormalUserMapper normalUserMapper;
    private final AuthMerchantMapper authMerchantMapper;

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