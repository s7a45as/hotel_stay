package com.homestay.modules.user.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.user.dto.UserPrivacyDTO;
import com.homestay.modules.user.entity.UserPrivacy;
import com.homestay.modules.user.mapper.UserPrivacyMapper;
import com.homestay.modules.user.service.UserPrivacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrivacyServiceImpl implements UserPrivacyService {

    private final UserPrivacyMapper userPrivacyMapper;

    @Override
    public UserPrivacyDTO getPrivacySettings() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrivacy privacy = userPrivacyMapper.selectByUserId(userId);
        if (privacy == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        UserPrivacyDTO privacyDTO = new UserPrivacyDTO();
        BeanUtils.copyProperties(privacy, privacyDTO);
        return privacyDTO;
    }

    @Override
    public void updatePrivacySettings(UserPrivacyDTO privacyDTO) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrivacy privacy = userPrivacyMapper.selectByUserId(userId);
        if (privacy == null) {
            privacy = new UserPrivacy();
            privacy.setUserId(userId);
        }
        
        BeanUtils.copyProperties(privacyDTO, privacy);
        
        if (privacy.getId() == null) {
            userPrivacyMapper.insert(privacy);
        } else {
            userPrivacyMapper.updateById(privacy);
        }
    }
} 