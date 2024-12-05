package com.homestay.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.homestay.modules.user.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {
    
    @Update("UPDATE t_user_message SET is_read = 1, read_time = NOW() " +
            "WHERE id = #{messageId} AND user_id = #{userId} AND deleted = 0")
    int markAsRead(@Param("messageId") Long messageId, @Param("userId") Long userId);
    
    @Update("<script>" +
            "UPDATE t_user_message SET is_read = 1, read_time = NOW() " +
            "WHERE user_id = #{userId} AND id IN " +
            "<foreach collection='messageIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND deleted = 0" +
            "</script>")
    int batchMarkAsRead(@Param("messageIds") List<Long> messageIds, @Param("userId") Long userId);
} 