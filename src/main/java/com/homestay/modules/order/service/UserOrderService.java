package com.homestay.modules.order.service;

import com.homestay.modules.order.dto.OrderCreateDTO;
import com.homestay.modules.order.vo.OrderDetailVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UserOrderService {
    
    /**
     * 创建订单
     */
    Object createOrder(OrderCreateDTO createDTO);
    
    /**
     * 获取订单详情
     */
    OrderDetailVO getOrderDetail(String orderId);
    
    /**
     * 获取订单列表
     */
    Page<?> getOrderList(Integer currentPage, Integer pageSize, Integer status);
    
    /**
     * 取消订单
     */
    void cancelOrder(String orderId);
    
    /**
     * 申请退款
     */
    void applyRefund(String orderId);
} 