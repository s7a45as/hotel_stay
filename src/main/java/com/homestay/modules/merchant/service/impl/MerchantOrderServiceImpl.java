package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.merchant.dto.OrderPageDTO;
import com.homestay.modules.merchant.entity.MerchantOrder;
import com.homestay.modules.merchant.mapper.MerchantOrderMapper;
import com.homestay.modules.merchant.service.MerchantOrderService;
import com.homestay.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MerchantOrderServiceImpl implements MerchantOrderService {

    private final MerchantOrderMapper orderMapper;

    @Override
    public OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status) {
        Page<MerchantOrder> page = new Page<>(currentPage, pageSize);
        
        LambdaQueryWrapper<MerchantOrder> wrapper = new LambdaQueryWrapper<MerchantOrder>()
            .eq(MerchantOrder::getMerchantId, SecurityUtils.getCurrentUserId())
            .eq(StringUtils.hasText(orderId), MerchantOrder::getOrderNo, orderId)
            .like(StringUtils.hasText(userName), MerchantOrder::getUserName, userName)
            .eq(StringUtils.hasText(status), MerchantOrder::getStatus, status)
            .orderByDesc(MerchantOrder::getCreateTime);
            
        Page<MerchantOrder> result = orderMapper.selectPage(page, wrapper);
        
        return OrderPageDTO.builder()
            .total(result.getTotal())
            .currentPage(currentPage)
            .pageSize(pageSize)
            .list(result.getRecords())
            .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleRefund(String orderId, Boolean agree) {
        MerchantOrder order = getOrderById(orderId);
        
        // 只能处理退款中的订单
        if (!order.getStatus().equals(4)) {
            throw new BusinessException(ResultCode.OPERATION_FAILED.getCode(), "只能处理退款中的订单");
        }
        
        // 更新订单状态
        order.setStatus(agree ? 5 : 1); // 5:已退款 1:已支付
        
        if (orderMapper.updateById(order) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
        
        // TODO: 如果同意退款,调用支付系统进行退款操作
        if (agree) {
            // refundService.refund(order);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderId, Integer status) {
        MerchantOrder order = getOrderById(orderId);
        order.setStatus(status);
        
        if (orderMapper.updateById(order) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    /**
     * 获取订单并校验权限
     */
    private MerchantOrder getOrderById(String orderId) {
        MerchantOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (!order.getMerchantId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        
        return order;
    }
} 