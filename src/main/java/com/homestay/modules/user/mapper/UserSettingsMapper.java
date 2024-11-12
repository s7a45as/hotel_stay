package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.user.entity.UserSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserSettingsMapper extends BaseMapper<UserSettings> {
    
    @Select("SELECT * FROM user_settings WHERE user_id = #{userId}")
    UserSettings selectByUserId(Long userId);
} 