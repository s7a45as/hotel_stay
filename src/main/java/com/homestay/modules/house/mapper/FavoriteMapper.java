package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.house.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
    
    /**
     * 检查用户是否已收藏房源
     */
    @Select("SELECT COUNT(*) FROM t_favorite WHERE user_id = #{userId} AND house_id = #{houseId} AND deleted = 0")
    int checkFavorite(Long userId, Long houseId);
    
    /**
     * 获取房源收藏数
     */
    @Select("SELECT COUNT(*) FROM t_favorite WHERE house_id = #{houseId} AND deleted = 0")
    int getFavoriteCount(Long houseId);
} 