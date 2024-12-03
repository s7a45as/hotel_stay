package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.house.entity.PromotionUsage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PromotionUsageMapper extends BaseMapper<PromotionUsage> {
    
    /**
     * 查询用户的优惠活动使用记录
     */
    @Select("SELECT * FROM t_promotion_usage WHERE user_id = #{userId} ORDER BY use_time DESC")
    List<PromotionUsage> findByUserId(Long userId);
    
    /**
     * 查询房源的优惠活动使用记录
     */
    @Select("SELECT * FROM t_promotion_usage WHERE house_id = #{houseId}")
    List<PromotionUsage> findByOrderId(String orderId);
    
    /**
     * 查询优惠活动的使用记录
     */
    @Select("SELECT * FROM t_promotion_usage WHERE promotion_id = #{promotionId} AND promotion_type = #{promotionType}")
    List<PromotionUsage> findByPromotion(Long promotionId, String promotionType);
} 