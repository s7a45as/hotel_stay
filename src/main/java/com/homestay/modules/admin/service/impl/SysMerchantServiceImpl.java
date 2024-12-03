package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.entity.SysMerchant;
import com.homestay.modules.admin.dto.MerchantAuditDTO;
import com.homestay.modules.admin.mapper.SysMerchantMapper;
import com.homestay.modules.admin.service.SysMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class SysMerchantServiceImpl extends ServiceImpl<SysMerchantMapper, SysMerchant> 
    implements SysMerchantService {

    @Override
    public Page<SysMerchant> getMerchantList(Integer pageNum, Integer pageSize, String keyword,
                                            String status, String startDate, String endDate) {
        LambdaQueryWrapper<SysMerchant> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like(SysMerchant::getBusinessName, keyword)
                   .or()
                   .like(SysMerchant::getContactPerson, keyword)
                   .or()
                   .like(SysMerchant::getPhone, keyword);
        }
        
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(SysMerchant::getStatus, convertStatus(status));
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(startDate)) {
            wrapper.ge(SysMerchant::getCreateTime, LocalDateTime.parse(startDate, formatter));
        }
        if (StringUtils.isNotBlank(endDate)) {
            wrapper.le(SysMerchant::getCreateTime, LocalDateTime.parse(endDate, formatter));
        }
        
        wrapper.orderByDesc(SysMerchant::getCreateTime);
        
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditMerchant(Long merchantId, MerchantAuditDTO auditDTO) {
        SysMerchant merchant = getById(merchantId);
        if (merchant == null) {
            throw new BusinessException("商家不存在");
        }
        
        merchant.setStatus("APPROVED".equals(auditDTO.getStatus()) ? 1 : 0);
        merchant.setAuditRemark(auditDTO.getReason());
        
        if (!updateById(merchant)) {
            throw new BusinessException("审核商家失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMerchantStatus(Long merchantId, String status) {
        SysMerchant merchant = getById(merchantId);
        if (merchant == null) {
            throw new BusinessException("商家不存在");
        }
        
        merchant.setStatus("NORMAL".equals(status) ? 1 : 0);
        
        if (!updateById(merchant)) {
            throw new BusinessException("更新商家状态失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMerchant(Long merchantId) {
        if (!removeById(merchantId)) {
            throw new BusinessException("删除商家失败");
        }
    }
    
    private Integer convertStatus(String status) {
        switch (status) {
            case "PENDING":
                return 2;
            case "APPROVED":
                return 1;
            case "REJECTED":
                return 0;
            default:
                throw new BusinessException("无效的状态值");
        }
    }
} 