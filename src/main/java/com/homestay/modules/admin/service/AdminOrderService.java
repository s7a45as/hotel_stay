package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.OrderPageDTO;

public interface AdminOrderService {
    
    OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status);
    
    void updateOrderStatus(String id, String status);
    
    void deleteOrder(String id);
} 