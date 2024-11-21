package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.user.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
    @Update("UPDATE sys_user SET ${sqlSet} WHERE id = #{id} AND deleted = 0")
    int updateById(@Param("et") UserInfo entity);

    @Select("SELECT * FROM sys_user WHERE email = #{email} AND deleted = 0")
    UserInfo selectByEmail(@Param("email") String email);

    @Select("SELECT * FROM sys_user WHERE phone = #{phone} AND deleted = 0")
    UserInfo selectByPhone(@Param("phone") String phone);
} 