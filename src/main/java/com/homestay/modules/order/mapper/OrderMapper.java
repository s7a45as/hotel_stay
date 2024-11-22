package com.homestay.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.modules.order.vo.OrderListVO;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.vo.OrderDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<UserOrder> {
    Page<OrderListVO> selectOrderList(Page<OrderListVO> page, @Param("userId") Long userId, @Param("status") Integer status);

    OrderDetailVO getOrderDetail(@Param("orderId") String orderId);
} 