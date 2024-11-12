package com.homestay.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.order.entity.OrderPayment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderPaymentMapper extends BaseMapper<OrderPayment> {
    
    @Select("SELECT * FROM t_order_payment WHERE payment_no = #{paymentNo}")
    OrderPayment selectByPaymentNo(String paymentNo);
    
    @Select("SELECT * FROM t_order_payment WHERE order_id = #{orderId}")
    OrderPayment selectByOrderId(String orderId);
} 