package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.house.entity.HouseFacility;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface HouseFacilityMapper extends BaseMapper<HouseFacility> {
    
    /**
     * 获取房源的设施列表（包含设施详细信息）
     */
    @Select("SELECT hf.*, f.name as facility_name, f.description, f.icon " +
            "FROM t_house_facility hf " +
            "LEFT JOIN t_facility f ON hf.facility_id = f.id " +
            "WHERE hf.house_id = #{houseId} AND hf.deleted = 0")
    List<HouseFacility> getFacilitiesByHouseId(Long houseId);
} 