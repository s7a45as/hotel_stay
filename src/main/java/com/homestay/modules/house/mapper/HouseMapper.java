package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.shareentity.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseMapper extends BaseMapper<House> {
    
    /**
     * 查询用户收藏的房源
     */
    Page<House> selectFavoritesByUserId(@Param("userId") Long userId, Page<House> page);
    
    // 其他前台特有的房源查询方法
} 