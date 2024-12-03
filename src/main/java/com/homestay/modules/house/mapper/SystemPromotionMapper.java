package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.home.entity.Promotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SystemPromotionMapper extends BaseMapper<Promotion> {
    
    /**
     * 获取指定时间范围内的有效优惠活动
     */
    @Select("SELECT * FROM t_sys_promotions " +
            "WHERE status = 1 " +
            "AND start_time <= #{endTime} " +
            "AND end_time >= #{startTime} " +
            "ORDER BY value DESC")
    List<Promotion> findValidPromotions(LocalDateTime startTime, LocalDateTime endTime);
} 