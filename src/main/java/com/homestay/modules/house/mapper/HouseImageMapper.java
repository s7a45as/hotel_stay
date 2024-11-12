package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.house.entity.HouseImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface HouseImageMapper extends BaseMapper<HouseImage> {
    
    /**
     * 获取房源图片列表
     */
    @Select("SELECT * FROM t_house_image WHERE house_id = #{houseId} AND deleted = 0 ORDER BY type DESC, sort ASC")
    List<HouseImage> selectByHouseId(Long houseId);
    
    /**
     * 获取房源封面图
     */
    @Select("SELECT * FROM t_house_image WHERE house_id = #{houseId} AND type = 1 AND deleted = 0 LIMIT 1")
    HouseImage selectCoverImage(Long houseId);
} 