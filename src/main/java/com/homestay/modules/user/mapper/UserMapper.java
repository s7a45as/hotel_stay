package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.user.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
    @Update("UPDATE sys_user SET " +
            "avatar = #{avatar}, " +
            "update_time = NOW(), " +
            "version = version + 1 " +
            "WHERE id = #{id} " +
            "AND deleted = 0 " +
            "AND version = #{version}")
    int updateAvatar(@Param("id") Long id, @Param("avatar") String avatar, @Param("version") Integer version);

    @Select("SELECT * FROM sys_user WHERE email = #{email} AND deleted = 0")
    UserInfo selectByEmail(@Param("email") String email);

    @Select("SELECT * FROM sys_user WHERE phone = #{phone} AND deleted = 0")
    UserInfo selectByPhone(@Param("phone") String phone);
} 