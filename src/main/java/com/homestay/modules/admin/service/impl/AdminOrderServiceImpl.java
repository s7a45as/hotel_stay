package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.dto.OrderPageDTO;
import com.homestay.modules.admin.entity.adminOrder;
import com.homestay.modules.admin.mapper.AdminOrderMapper;
import com.homestay.modules.admin.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AdminOrderMapper adminOrderMapper;

    @Override
    public OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status) {
        Page<adminOrder> page = new Page<>(currentPage, pageSize);
        
        LambdaQueryWrapper<adminOrder> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(orderId)) {
            wrapper.eq(adminOrder::getId, orderId);
        }
        if (StringUtils.hasText(userName)) {
            wrapper.like(adminOrder::getUserName, userName);
        }
        if (StringUtils.hasText(status)) {
            wrapper.eq(adminOrder::getStatus, status);
        }
        wrapper.orderByDesc(adminOrder::getCreateTime);
        
        Page<adminOrder> orderPage = adminOrderMapper.selectPage(page, wrapper);
        
        return OrderPageDTO.builder()
                .total(orderPage.getTotal())
                .currentPage(currentPage)
                .pageSize(pageSize)
                .list(orderPage.getRecords())
                .build();
    }

    @Override
    public void updateOrderStatus(String id, String status) {
        adminOrder adminOrder = adminOrderMapper.selectById(id);
        if (adminOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        adminOrder updateAdminOrder = new adminOrder();
        updateAdminOrder.setId(id);
        updateAdminOrder.setStatus(status);
        adminOrderMapper.updateById(updateAdminOrder);
    }

    @Override
    public void deleteOrder(String id) {
        adminOrderMapper.deleteById(id);
    }
} 