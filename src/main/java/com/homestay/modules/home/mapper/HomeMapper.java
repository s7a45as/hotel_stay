package com.homestay.modules.home.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.modules.home.entity.homeCity;
import com.homestay.modules.home.entity.homeDistricts;
import com.homestay.common.shareentity.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HomeMapper extends BaseMapper<House> {

    @Select("SELECT code, name FROM t_cities WHERE status = 1 ")
    List<homeCity> selectCityList();

    @Select("SELECT * FROM t_districts WHERE status = 1 ORDER BY id limit 10")
    List<homeDistricts> selectPopularDestinations();

    /**
     * 搜索房源
     */
    Page<House> searchHouses(Page<House> page,
                           @Param("keyword") String keyword,
                           @Param("city") String city,
                           @Param("type") String type,
                           @Param("minPrice") Integer minPrice,
                           @Param("maxPrice") Integer maxPrice);
}