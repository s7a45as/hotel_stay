package com.homestay.modules.house.mapper;

import com.homestay.modules.house.entity.THouseComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

/**
* @author 45732
* @description 针对表【t_house_comment(房源评论表)】的数据库操作Mapper
* @createDate 2024-12-01 14:27:35
* @Entity com.homestay.modules.house.entity.THouseComment
*/
@Mapper
public interface THouseCommentMapper extends BaseMapper<THouseComment> {

    /**
     * 获取房源评论列表
     */
    @Select("SELECT c.*, u.nickname, u.avatar " +
            "FROM t_house_comment c " +
            "LEFT JOIN t_user u ON c.user_id = u.id " +
            "WHERE c.house_id = #{houseId} AND c.status = 1 " +
            "ORDER BY c.create_time DESC")
    IPage<THouseComment> selectCommentsByHouseId(@Param("houseId") Long houseId, Page<THouseComment> page);
    
    /**
     * 获取用户是否已点赞评论
     */
    @Select("SELECT COUNT(*) FROM t_house_comment_like " +
            "WHERE comment_id = #{commentId} AND user_id = #{userId}")
    int checkCommentLike(@Param("commentId") Long commentId, @Param("userId") Long userId);

    /**
     * 获取评论点赞数
     */
    @Select("SELECT COUNT(*) FROM t_house_comment_like WHERE comment_id = #{commentId}")
    int getCommentLikeCount(@Param("commentId") Long commentId);
}




