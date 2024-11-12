package com.homestay.modules.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.common.shareentity.House;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminHouseMapper extends BaseMapper<House> {
    // 管理后台特有的房源查询方法
} 