package com.homestay.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.modules.admin.entity.SysPromotion;
import com.homestay.modules.admin.dto.SysPromotionDTO;

public interface SysPromotionService extends IService<SysPromotion> {
    
    Page<SysPromotion> getPromotionList(Integer pageNum, Integer pageSize, String keyword, 
                                       String status, String startDate, String endDate);
    
    void createPromotion(SysPromotionDTO promotionDTO);
    
    void updatePromotion(Long id, SysPromotionDTO promotionDTO);
    
    void deletePromotion(Long id);
} 