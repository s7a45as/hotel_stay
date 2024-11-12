package com.homestay.modules.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.auth.entity.AuthMerchant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMerchantMapper extends BaseMapper<AuthMerchant> {
    // 继承BaseMapper后已经有基础的CRUD方法，如果需要自定义方法可以在这里添加
} 