package com.homestay.modules.payment.service.impl;

import com.homestay.common.exception.BusinessException;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.mapper.OrderMapper;
import com.homestay.modules.payment.vo.PaymentQRCodeVo;
import com.homestay.modules.payment.dto.PaymentRequestDTO;
import com.homestay.modules.payment.dto.PaymentStatusDTO;
import com.homestay.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final OrderMapper orderMapper;
    private final ResourceLoader resourceLoader;
    
    // 修改为正确的图片路径
    private static final String WX_QR_CODE = "classpath:image/wx/aabf590bf12931e6e567a36abb82eec.jpg";
    private static final String ALIPAY_QR_CODE = "classpath:image/zfb/d66e1f733ccdb679299cd1578b14f85.jpg";

    @Override
    public PaymentQRCodeVo generatePayQRCode(PaymentRequestDTO request) {
        // 1. 验证订单
        UserOrder userOrder = orderMapper.selectById(request.getOrderId());
        if (userOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 2. 验证订单状态
        if (userOrder.getStatus() != 0) { // 0: 待支付
            throw new BusinessException("订单状态不正确");
        }

        // 3. 根据支付方式读取对应的收款码图片
        String qrCodeBase64;
        String paymentMethod = request.getPaymentMethod();
        try {
            String imagePath;
            if ("WECHAT".equals(paymentMethod)) {
                imagePath = WX_QR_CODE;
            } else if ("ALIPAY".equals(paymentMethod)) {
                imagePath = ALIPAY_QR_CODE;
            } else {
                throw new BusinessException("不支持的支付方式");
            }
            
            // 使用ClassPathResource加载图片
            Resource resource = new ClassPathResource(imagePath.replace("classpath:", ""));
            if (!resource.exists()) {
                throw new BusinessException("收款码图片不存在");
            }
            
            // 读取图片并转换为Base64
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            qrCodeBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes);
            
        } catch (IOException e) {
            log.error("获取收款码失败", e);
            throw new BusinessException("获取收款码失败: " + e.getMessage());
        }

        // 4. 构建返回对象
        return PaymentQRCodeVo.builder()
                .qrCodeBase64(qrCodeBase64)
                .orderId(request.getOrderId())
                .amount(userOrder.getAmount())
                .paymentMethod(paymentMethod)
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