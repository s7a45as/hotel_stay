package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.user.entity.UserInfoSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserSettingsMapper extends BaseMapper<UserInfoSettings> {
    
    @Select("SELECT * FROM t_user_settings WHERE user_id = #{userId}")
    UserInfoSettings selectByUserId(Long userId);
} 