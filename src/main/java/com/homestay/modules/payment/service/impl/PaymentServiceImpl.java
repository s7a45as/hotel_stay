package com.homestay.modules.payment.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.mapper.OrderMapper;
import com.homestay.modules.payment.dto.PaymentQRCodeDTO;
import com.homestay.modules.payment.dto.PaymentRequestDTO;
import com.homestay.modules.payment.dto.PaymentStatusDTO;
import com.homestay.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderMapper orderMapper;

    @Override
    public PaymentQRCodeDTO generatePayQRCode(PaymentRequestDTO request) {
        // 1. 验证订单
        UserOrder userOrder = orderMapper.selectById(request.getOrderId());
        if (userOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 生成支付二维码（这里模拟生成）
        return PaymentQRCodeDTO.builder()
                .qrCodeUrl("https://example.com/pay/" + request.getOrderId())
                .orderId(request.getOrderId())
                .amount(userOrder.getAmount())
                .expireTime(System.currentTimeMillis() + 30 * 60 * 1000) // 30分钟有效期
                .build();
    }

    @Override
    public PaymentStatusDTO getPaymentStatus(String orderId) {
        // 1. 查询订单
        UserOrder userOrder = orderMapper.selectById(orderId);
        if (userOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 返回支付状态
        return PaymentStatusDTO.builder()
                .orderId(orderId)
                .status(userOrder.getStatus())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentCallback(String orderId, String status) {
        // 1. 查询订单
        UserOrder userOrder = orderMapper.selectById(orderId);
        if (userOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 处理支付结果
        if ("SUCCESS".equals(status)) {
            userOrder.setStatus(1); // 已支付
        } else if ("FAIL".equals(status)) {
            userOrder.setStatus(0); // 支付失败
        } else {
            throw new BusinessException("无效的支付状态");
        }
        
        // 3. 更新订单状态
        orderMapper.updateById(userOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pay(String orderId) {
        // 1. 获取订单信息
        UserOrder userOrder = orderMapper.selectById(orderId);
        if (userOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 检查订单状态
        if (userOrder.getStatus() != 0) { // 0: 待支付
            throw new BusinessException("订单状态不正确");
        }
        
        // 3. 调用支付接口（这里模拟支付成功）
        // TODO: 实现实际的支付逻辑
        
        // 4. 更新订单状态为已支付
        userOrder.setStatus(1); // 1: 已支付
        orderMapper.updateById(userOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyRefund(String orderId, String reason) {
        // 1. 获取订单信息
        UserOrder userOrder = orderMapper.selectById(orderId);
        if (userOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 检查订单状态
        if (userOrder.getStatus() != 1) { // 1: 已支付
            throw new BusinessException("订单状态不正确");
        }
        
        // 3. 调用退款接口（这里模拟退款成功）
        // TODO: 实现实际的退款逻辑
        
        // 4. 更新订单状态为已取消
        userOrder.setStatus(2); // 2: 已取消
//        userOrder.setRefundReason(reason);
        orderMapper.updateById(userOrder);
    }
} 