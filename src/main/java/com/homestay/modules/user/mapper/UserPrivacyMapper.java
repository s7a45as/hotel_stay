package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.user.entity.UserInfoPrivacy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserPrivacyMapper extends BaseMapper<UserInfoPrivacy> {
    
    @Select("SELECT * FROM user_privacy WHERE user_id = #{userId}")
    UserInfoPrivacy selectByUserId(Long userId);
} 