package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.OrderPageDTO;

public interface AdminOrderService {
    
    OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status);
    
    void updateOrderStatus(String id, String status);
    
    void deleteOrder(String id);
    
    /**
     * 导出订单数据
     * @param orderId 订单ID
     * @param userName 用户名
     * @param status 订单状态
     * @return 导出的文件数据
     */
    byte[] exportOrders(String orderId, String userName, String status);
} 