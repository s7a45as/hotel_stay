package com.homestay.modules.payment.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.modules.order.entity.Order;
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
        Order order = orderMapper.selectById(request.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 生成支付二维码（这里模拟生成）
        return PaymentQRCodeDTO.builder()
                .qrCodeUrl("https://example.com/pay/" + request.getOrderId())
                .orderId(request.getOrderId())
                .amount(order.getAmount())
                .expireTime(System.currentTimeMillis() + 30 * 60 * 1000) // 30分钟有效期
                .build();
    }

    @Override
    public PaymentStatusDTO getPaymentStatus(String orderId) {
        // 1. 查询订单
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 返回支付状态
        return PaymentStatusDTO.builder()
                .orderId(orderId)
                .status(order.getStatus())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentCallback(String orderId, String status) {
        // 1. 查询订单
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 处理支付结果
        if ("SUCCESS".equals(status)) {
            order.setStatus(1); // 已支付
        } else if ("FAIL".equals(status)) {
            order.setStatus(0); // 支付失败
        } else {
            throw new BusinessException("无效的支付状态");
        }
        
        // 3. 更新订单状态
        orderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pay(String orderId) {
        // 1. 获取订单信息
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 检查订单状态
        if (order.getStatus() != 0) { // 0: 待支付
            throw new BusinessException("订单状态不正确");
        }
        
        // 3. 调用支付接口（这里模拟支付成功）
        // TODO: 实现实际的支付逻辑
        
        // 4. 更新订单状态为已支付
        order.setStatus(1); // 1: 已支付
        orderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyRefund(String orderId, String reason) {
        // 1. 获取订单信息
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 检查订单状态
        if (order.getStatus() != 1) { // 1: 已支付
            throw new BusinessException("订单状态不正确");
        }
        
        // 3. 调用退款接口（这里模拟退款成功）
        // TODO: 实现实际的退款逻辑
        
        // 4. 更新订单状态为已取消
        order.setStatus(2); // 2: 已取消
//        order.setRefundReason(reason);
        orderMapper.updateById(order);
    }
} 