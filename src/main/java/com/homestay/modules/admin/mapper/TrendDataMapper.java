package com.homestay.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.admin.entity.TrendData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface TrendDataMapper extends BaseMapper<TrendData> {

    /**
     * 获取最近6个月的用户趋势数据
     */
    @Select("SELECT DATE_FORMAT(date, '%m月') as month, count " +
            "FROM t_trend_data " +
            "WHERE type = 'USER' " +
            "AND date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
            "ORDER BY date ASC")
    List<Map<String, Object>> getUserTrendData();

    /**
     * 获取最近6个月的订单趋势数据
     */
    @Select("SELECT DATE_FORMAT(date, '%m月') as month, count " +
            "FROM t_trend_data " +
            "WHERE type = 'ORDER' " +
            "AND date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
            "ORDER BY date ASC")
    List<Map<String, Object>> getOrderTrendData();
} 