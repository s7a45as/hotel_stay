package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "商家仪表盘统计数据")
public class DashboardStatisticsDTO {
    
    @Schema(description = "总房源数")
    private Integer totalHouses;
    
    @Schema(description = "总订单数")
    private Integer totalOrders;
    
    @Schema(description = "总收入")
    private BigDecimal totalIncome;
    
    @Schema(description = "待处理订单数")
    private Integer pendingOrders;
} 