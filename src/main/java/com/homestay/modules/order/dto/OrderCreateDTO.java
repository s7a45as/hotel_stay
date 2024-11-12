package com.homestay.modules.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "创建订单请求参数")
public class OrderCreateDTO {
    
    @NotNull(message = "房源ID不能为空")
    @Schema(description = "房源ID")
    private Long houseId;
    
    @NotNull(message = "入住日期不能为空")
    @Future(message = "入住日期必须是将来的日期")
    @Schema(description = "入住日期")
    private LocalDate checkIn;
    
    @NotNull(message = "退房日期不能为空")
    @Future(message = "退房日期必须是将来的日期")
    @Schema(description = "退房日期")
    private LocalDate checkOut;
    
    @NotNull(message = "入住人数不能为空")
    @Min(value = 1, message = "入住人数必须大于0")
    @Schema(description = "入住人数")
    private Integer guests;
    
    @NotNull(message = "联系人姓名不能为空")
    @Schema(description = "联系人姓名")
    private String contactName;
    
    @NotNull(message = "联系人电话不能为空")
    @Schema(description = "联系人电话")
    private String contactPhone;
    
    @Schema(description = "特殊要求")
    private String specialRequests;
} 