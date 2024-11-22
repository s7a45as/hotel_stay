package com.homestay.modules.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单列表返回对象")
public class OrderListVO {
    
    @Schema(description = "订单ID")
    private String id;
    
    @Schema(description = "房源名称")
    private String houseName;
    
    @Schema(description = "房源图片")
    private String houseImage;
    
    @Schema(description = "入住日期")
    private LocalDate checkIn;
    
    @Schema(description = "退房日期")
    private LocalDate checkOut;
    
    @Schema(description = "入住天数")
    private Integer nights;
    
    @Schema(description = "入住人数")
    private Integer guests;
    
    @Schema(description = "订单金额")
    private BigDecimal amount;
    
    @Schema(description = "订单状态")
    private String status;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
} 