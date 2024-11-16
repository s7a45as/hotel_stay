package com.homestay.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.admin.entity.adminOrder;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AdminOrderMapper extends BaseMapper<adminOrder> {
    
    /**
     * 统计订单总数
     */
    @Select("SELECT COUNT(*) FROM t_order")
    Integer countTotalOrders();
    
    /**
     * 统计总收入
     */
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM t_order WHERE status = 1")
    BigDecimal sumTotalRevenue();
} 