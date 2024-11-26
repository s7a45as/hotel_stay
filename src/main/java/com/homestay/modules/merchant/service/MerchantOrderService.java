package com.homestay.modules.merchant.service;

import com.homestay.modules.merchant.dto.MerchantOrderDetailDTO;
import com.homestay.modules.merchant.dto.OrderPageDTO;
import com.homestay.modules.order.dto.OrderDetailDTO;

public interface MerchantOrderService {

    /**
     * 获取订单列表
     */
    OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status);

    /**
     * 处理退款申请
     */
    void handleRefund(String orderId, Boolean agree);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(String orderId, Integer status);

    MerchantOrderDetailDTO getOrderDetail(String orderId);
} 