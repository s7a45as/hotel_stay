package com.homestay.modules.merchant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.merchant.dto.MerchantOrderDetailDTO;
import com.homestay.modules.merchant.entity.MerchantOrder;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MerchantOrderMapper extends BaseMapper<MerchantOrder> {
    MerchantOrderDetailDTO getOrderDetail(@Param("orderId") String orderId);
} 