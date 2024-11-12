package com.homestay.modules.merchant.service;

import com.homestay.modules.merchant.dto.MerchantProfileDTO;
import com.homestay.modules.merchant.dto.UpdatePasswordDTO;
import com.homestay.modules.merchant.entity.Merchant;
import org.springframework.web.multipart.MultipartFile;

public interface MerchantProfileService {

    /**
     * 获取商家信息
     */
    Merchant getProfile();

    /**
     * 更新商家信息
     */
    void updateProfile(MerchantProfileDTO profileDTO);

    /**
     * 修改密码
     */
    void updatePassword(UpdatePasswordDTO passwordDTO);

    /**
     * 上传头像
     */
    String uploadAvatar(MultipartFile file);
} 