package com.homestay.modules.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.auth.entity.NormalUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NormalUserMapper extends BaseMapper<NormalUser> {
} 