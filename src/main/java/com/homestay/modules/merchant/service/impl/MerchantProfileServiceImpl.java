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
        String avatarUrl = uploadUtils.upload(file, "avatar");

        // 更新商家头像
        Merchant merchant = merchantMapper.selectById(SecurityUtils.getCurrentUserId());
        merchant.setAvatar(avatarUrl);
        if (merchantMapper.updateById(merchant) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }

        return avatarUrl;
    }
} 