package com.homestay.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.modules.order.vo.OrderListVO;
import com.homestay.modules.order.entity.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper extends BaseMapper<UserOrder> {
    
    @Select("""
        SELECT 
            o.id,
            h.title as house_name,
            h.image as house_image,
            o.check_in,
            o.check_out,
            o.nights,
            o.guests,
            o.amount,
            o.status,
            o.create_time
        FROM t_user_order o
        LEFT JOIN house h ON o.house_id = h.id
        WHERE o.user_id = #{userId}
        ${status != null ? "AND o.status = #{status}" : ""}
        ORDER BY o.create_time DESC
    """)
    Page<OrderListVO> selectOrderList(Page<OrderListVO> page, Long userId, Integer status);
} 