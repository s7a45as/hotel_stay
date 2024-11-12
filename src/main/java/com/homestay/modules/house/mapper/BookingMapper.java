package com.homestay.modules.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.modules.house.entity.houseBooking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface BookingMapper extends BaseMapper<houseBooking> {
    
    /**
     * 检查指定时间段是否有冲突的预订
     *
     * @param houseId 房源ID
     * @param checkInTime 入住时间
     * @param checkOutTime 退房时间
     * @return 冲突的预订数量
     */
    @Select("SELECT COUNT(*) FROM t_booking " +
            "WHERE house_id = #{houseId} " +
            "AND status != 2 " + // 排除已取消的订单
            "AND (" +
            "   (check_in_time <= #{checkInTime} AND check_out_time > #{checkInTime}) OR " +
            "   (check_in_time < #{checkOutTime} AND check_out_time >= #{checkOutTime}) OR " +
            "   (check_in_time >= #{checkInTime} AND check_out_time <= #{checkOutTime})" +
            ")")
    int checkBookingConflict(@Param("houseId") Long houseId,
                            @Param("checkInTime") LocalDateTime checkInTime,
                            @Param("checkOutTime") LocalDateTime checkOutTime);
} 