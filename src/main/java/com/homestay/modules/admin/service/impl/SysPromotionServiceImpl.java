package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.entity.SysPromotion;
import com.homestay.modules.admin.dto.SysPromotionDTO;
import com.homestay.modules.admin.mapper.SysPromotionMapper;
import com.homestay.modules.admin.service.SysPromotionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class SysPromotionServiceImpl extends ServiceImpl<SysPromotionMapper, SysPromotion> 
    implements SysPromotionService {

    @Override
    public Page<SysPromotion> getPromotionList(Integer pageNum, Integer pageSize, String keyword,
                                              String status, String startDate, String endDate) {
        LambdaQueryWrapper<SysPromotion> wrapper = new LambdaQueryWrapper<>();
        
        // 关键词搜索
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like(SysPromotion::getTitle, keyword)
                   .or()
                   .like(SysPromotion::getDescription, keyword);
        }
        
        // 状态筛选
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(SysPromotion::getStatus, Integer.valueOf(status));
        }
        
        // 日期范围筛选
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(startDate)) {
            wrapper.ge(SysPromotion::getStartTime, LocalDateTime.parse(startDate, formatter));
        }
        if (StringUtils.isNotBlank(endDate)) {
            wrapper.le(SysPromotion::getEndTime, LocalDateTime.parse(endDate, formatter));
        }
        
        // 按创建时间降序排序
        wrapper.orderByDesc(SysPromotion::getCreatedAt);
        
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPromotion(SysPromotionDTO promotionDTO) {
        validatePromotionTime(promotionDTO);
        log.debug("创建优惠活动：{}", promotionDTO);
        SysPromotion promotion = new SysPromotion();
        BeanUtils.copyProperties(promotionDTO, promotion);
        
        // 设置初始状态
        promotion.setStatus(determineStatus(promotionDTO.getStartTime(), promotionDTO.getEndTime()));
        
        if (!save(promotion)) {
            throw new BusinessException("创建优惠活动失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePromotion(Long id, SysPromotionDTO promotionDTO) {
        SysPromotion promotion = getById(id);
        if (promotion == null) {
            throw new BusinessException("优惠活动不存在");
        }
        
        validatePromotionTime(promotionDTO);
        
        BeanUtils.copyProperties(promotionDTO, promotion);
        promotion.setStatus(determineStatus(promotionDTO.getStartTime(), promotionDTO.getEndTime()));
        
        if (!updateById(promotion)) {
            throw new BusinessException("更新优惠活动失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePromotion(Long id) {
        if (!removeById(id)) {
            throw new BusinessException("删除优惠活动失败");
        }
    }
    
    private void validatePromotionTime(SysPromotionDTO promotionDTO) {
        if (promotionDTO.getStartTime().isAfter(promotionDTO.getEndTime())) {
            throw new BusinessException("活动开始时间不能晚于结束时间");
        }
    }
    
    private Integer determineStatus(LocalDateTime startTime, LocalDateTime endTime) {
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