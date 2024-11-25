package com.homestay.modules.merchant.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.merchant.dto.MerchantProfileDTO;
import com.homestay.modules.merchant.dto.UpdatePasswordDTO;
import com.homestay.modules.merchant.entity.Merchant;
import com.homestay.modules.merchant.mapper.MerchantMapper;
import com.homestay.modules.merchant.service.MerchantProfileService;
import com.homestay.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MerchantProfileServiceImpl implements MerchantProfileService {

    private final MerchantMapper merchantMapper;
    private final PasswordEncoder passwordEncoder;
    private final UploadUtils uploadUtils;

    @Override
    public Merchant getProfile() {
        Merchant merchant = merchantMapper.selectById(SecurityUtils.getCurrentUserId());
        if (merchant == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        // 清除敏感信息
        merchant.setPassword(null);
        merchant.setIdCard(null);
        return merchant;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(MerchantProfileDTO profileDTO) {
        Merchant merchant = merchantMapper.selectById(SecurityUtils.getCurrentUserId());
        if (merchant == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }

        BeanUtils.copyProperties(profileDTO, merchant);
        if (merchantMapper.updateById(merchant) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordDTO passwordDTO) {
        // 校验新密码与确认密码是否一致
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new BusinessException(ResultCode.TWO_PASSWORDS_NOT_MATCH);
        }

        Merchant merchant = merchantMapper.selectById(SecurityUtils.getCurrentUserId());
        if (merchant == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }

        // 校验原密码是否正确
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), merchant.getPassword())) {
            throw new BusinessException(ResultCode.OLD_PASSWORD_ERROR);
        }

        // 更新密码
        merchant.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        if (merchantMapper.updateById(merchant) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadAvatar(MultipartFile file) {
        // 上传文件
        String avatarUrl = uploadUtils.upload(file, "merchant_avatar");

        // 更新商家头像
        Merchant merchant = merchantMapper.selectById(SecurityUtils.getCurrentUserId());
        merchant.setAvatar(avatarUrl);
        if (merchantMapper.updateById(merchant) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }

        return avatarUrl;
    }

    @Override
    public List<Map<String, Object>> getMenus() {
        List<Map<String, Object>> menus = new ArrayList<>();
        
        // 仪表盘
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("id", 1);
        dashboard.put("name", "仪表盘");
        dashboard.put("icon", "dashboard");
        dashboard.put("path", "/merchant/dashboard");
        menus.add(dashboard);
        
        // 房源管理
        Map<String, Object> house = new HashMap<>();
        house.put("id", 2);
        house.put("name", "房源管理");
        house.put("icon", "house"); 
        house.put("path", "/merchant/houses");
        menus.add(house);
        
        // 订单管理
        Map<String, Object> order = new HashMap<>();
        order.put("id", 3);
        order.put("name", "订单管理");
        order.put("icon", "order");
        order.put("path", "/merchant/orders");
        menus.add(order);
        
        // 个人信息
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", 4);
        profile.put("name", "个人信息");
        profile.put("icon", "user");
        profile.put("path", "/merchant/info");
        menus.add(profile);
        
        return menus;
    }
} 