package com.homestay.modules.payment.service;

import com.homestay.modules.payment.vo.PaymentQRCodeVo;
import com.homestay.modules.payment.dto.PaymentRequestDTO;
import com.homestay.modules.payment.dto.PaymentStatusDTO;

public interface PaymentService {
    
    /**
     * 生成支付二维码
     *
     * @param request 支付请求参数
     * @return 支付二维码信息
     */
    PaymentQRCodeVo generatePayQRCode(PaymentRequestDTO request);

    /**
     * 查询支付状态
     *
     * @param orderId 订单ID
     * @return 支付状态信息
     */
    PaymentStatusDTO getPaymentStatus(String orderId);

    /**
     * 处理支付回调
     *
     * @param orderId 订单ID
     * @param status 支付状态
     */
    void handlePaymentCallback(String orderId, String status);
    
    /**
     * 支付订单
     *
     * @param orderId 订单ID
     */
    void pay(String orderId);
    
    /**
     * 申请退款
     *
     * @param orderId 订单ID
     * @param reason 退款原因
     */
    void applyRefund(String orderId, String reason);
} 