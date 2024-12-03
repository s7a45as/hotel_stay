package com.homestay.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.modules.admin.entity.SysMerchant;
import com.homestay.modules.admin.dto.MerchantAuditDTO;

public interface SysMerchantService extends IService<SysMerchant> {
    
    Page<SysMerchant> getMerchantList(Integer pageNum, Integer pageSize, String keyword, 
                                     String status, String startDate, String endDate);
    
    void auditMerchant(Long merchantId, MerchantAuditDTO auditDTO);
    
    void updateMerchantStatus(Long merchantId, String status);
    
    void deleteMerchant(Long merchantId);
} 