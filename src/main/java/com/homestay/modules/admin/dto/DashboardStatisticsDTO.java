package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "仪表盘统计数据")
public class DashboardStatisticsDTO {
    
    @Schema(description = "总用户数", example = "1234",
           title = "包含所有状态的用户总数")
    private Integer totalUsers;
    
    @Schema(description = "总订单数", example = "5678",
           title = "包含所有状态的订单总数")
    private Integer totalOrders;
    
    @Schema(description = "总房源数", example = "890",
           title = "包含所有状态的房源总数")
    private Integer totalHouses;
    
    @Schema(description = "总收入(元)", example = "123456.78",
           title = "所有已完成订单的收入总和")
    private BigDecimal revenue;
} 