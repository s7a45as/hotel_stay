package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.modules.merchant.entity.MerchantPromotion;
import com.homestay.modules.merchant.dto.MerchantPromotionDTO;
import com.homestay.modules.merchant.mapper.MerchantPromotionMapper;
import com.homestay.modules.merchant.service.MerchantPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantPromotionServiceImpl implements MerchantPromotionService {

    private final MerchantPromotionMapper promotionMapper;

    @Override
    public List<MerchantPromotion> getPromotionList() {
        return promotionMapper.selectList(
            new LambdaQueryWrapper<MerchantPromotion>()
                .eq(MerchantPromotion::getMerchantId, SecurityUtils.getCurrentUserId())
                .orderByDesc(MerchantPromotion::getCreateTime)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPromotion(MerchantPromotionDTO promotionDTO) {
        validatePromotionTime(promotionDTO);
        
        MerchantPromotion promotion = new MerchantPromotion();
        BeanUtils.copyProperties(promotionDTO, promotion);
        
        promotion.setMerchantId(SecurityUtils.getCurrentUserId());
        promotion.setStatus(getPromotionStatus(promotionDTO.getStartTime(), promotionDTO.getEndTime()));
        
        if (promotionMapper.insert(promotion) != 1) {
            throw new BusinessException(ResultCode.DATA_ADD_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePromotion(Long id, MerchantPromotionDTO promotionDTO) {
        validatePromotionTime(promotionDTO);
        
        MerchantPromotion promotion = getPromotionById(id);
        BeanUtils.copyProperties(promotionDTO, promotion);
        
        promotion.setStatus(getPromotionStatus(promotionDTO.getStartTime(), promotionDTO.getEndTime()));
        
        if (promotionMapper.updateById(promotion) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePromotion(Long id) {
        MerchantPromotion promotion = getPromotionById(id);
        
        if (promotionMapper.deleteById(id) != 1) {
            throw new BusinessException(ResultCode.DATA_DELETE_FAILED);
        }
    }

    /**
     * 获取活动并校验权限
     */
    private MerchantPromotion getPromotionById(Long id) {
        MerchantPromotion promotion = promotionMapper.selectById(id);
        if (promotion == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (!promotion.getMerchantId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        
        return promotion;
    }

    /**
     * 校验活动时间
     */
    private void validatePromotionTime(MerchantPromotionDTO promotionDTO) {
        if (promotionDTO.getStartTime().isAfter(promotionDTO.getEndTime())) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "开始时间不能晚于结束时间");
        }
        
        if (promotionDTO.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "开始时间不能早于当前时间");
        }
    }

    /**
     * 获取活动状态
     */
    private Integer getPromotionStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            return 0; // 未开始
        } else if (now.isAfter(endTime)) {
            return 2; // 已结束
        } else {
            return 1; // 进行中
        }
    }
} 