package com.homestay.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.admin.entity.adminOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;

@Mapper
public interface AdminOrderMapper extends BaseMapper<adminOrder> {
    
    @Select("SELECT COUNT(*) FROM t_order WHERE deleted = 0")
    Integer countTotalOrders();
    
    @Select("SELECT SUM(total_amount) FROM t_order WHERE status = 'PAID' AND deleted = 0")
    BigDecimal sumTotalRevenue();
} 