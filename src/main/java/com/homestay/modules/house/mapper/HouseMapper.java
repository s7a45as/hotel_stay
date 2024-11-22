package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.shareentity.House;
import com.homestay.modules.house.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HouseMapper extends BaseMapper<House> {
    
    /**
     * 查询用户收藏的房源
     */
    Page<House> selectFavoritesByUserId(@Param("userId") Long userId, Page<House> page);
    
    // 其他前台特有的房源查询方法
    
    @Select("SELECT t.* FROM house_tag ht " +
            "JOIN tag t ON ht.tag_id = t.id " +
            "WHERE ht.house_id = #{houseId}")
    List<Tag> selectHouseTags(Long houseId);
} 