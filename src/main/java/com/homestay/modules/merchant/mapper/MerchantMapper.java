package com.homestay.modules.merchant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.merchant.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {
    
    /**
     * 根据ID查询商家信息
     */
    @Select("SELECT id, username, nickname, email, phone, avatar, status, " +
            "business_name, business_address as address " +
            "FROM sys_merchant " +
            "WHERE id = #{id} AND deleted = 0 AND status = 1")
    Merchant getMerchantById(Long id);
} 