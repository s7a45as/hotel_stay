package com.homestay.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.modules.order.vo.OrderListVO;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.vo.OrderDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface OrderMapper extends BaseMapper<UserOrder> {
    Page<OrderListVO> selectOrderList(Page<OrderListVO> page, @Param("userId") Long userId, @Param("status") Integer status);

    OrderDetailVO getOrderDetail(@Param("orderId") String orderId);

    /**
     * 物理删除订单
     * @param orderId 订单ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM t_user_order WHERE id = #{orderId}")
    int physicalDeleteById(@Param("orderId") String orderId);
} 