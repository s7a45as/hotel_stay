package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.user.entity.UserPrivacy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserPrivacyMapper extends BaseMapper<UserPrivacy> {
    
    @Select("SELECT * FROM user_privacy WHERE user_id = #{userId}")
    UserPrivacy selectByUserId(Long userId);
} 