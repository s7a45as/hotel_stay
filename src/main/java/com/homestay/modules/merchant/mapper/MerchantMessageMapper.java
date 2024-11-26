package com.homestay.modules.merchant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.merchant.entity.MerchantMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 商家消息Mapper接口
 */
@Mapper
public interface MerchantMessageMapper extends BaseMapper<MerchantMessage> {
    @Select("SELECT COUNT(*) FROM t_merchant_message WHERE merchant_id = #{merchantId} AND is_read = false")
    Integer countUnreadMessages(@Param("merchantId") Long merchantId);
} 