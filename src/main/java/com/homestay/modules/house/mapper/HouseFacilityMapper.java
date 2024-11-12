package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.house.entity.Facility;
import com.homestay.modules.house.entity.HouseFacility;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface HouseFacilityMapper extends BaseMapper<HouseFacility> {
    
    /**
     * 获取房源设施列表
     */
    @Select("SELECT f.* FROM t_facility f " +
            "INNER JOIN t_house_facility hf ON f.id = hf.facility_id " +
            "WHERE hf.house_id = #{houseId} AND hf.deleted = 0 " +
            "ORDER BY f.id ASC")
    List<Facility> selectFacilitiesByHouseId(Long houseId);
} 