package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "预订请求参数")
public class BookingDTO {
    
    @NotNull(message = "房源ID不能为空")
    @Schema(description = "房源ID")
    private Long houseId;
    
    @NotNull(message = "入住时间不能为空")
    @Future(message = "入住时间必须是将来时间")
    @Schema(description = "入住时间")
    private LocalDateTime checkInTime;
    
    @NotNull(message = "退房时间不能为空")
    @Future(message = "退房时间必须是将来时间")
    @Schema(description = "退房时间")
    private LocalDateTime checkOutTime;
    
    @NotNull(message = "入住人数不能为空")
    @Min(value = 1, message = "入住人数必须大于0")
    @Schema(description = "入住人数")
    private Integer guestCount;
    
    @NotBlank(message = "联系人姓名不能为空")
    @Schema(description = "联系人姓名")
    private String contactName;
    
    @NotBlank(message = "联系人电话不能为空")
    @Schema(description = "联系人电话")
    private String contactPhone;
    
    @Schema(description = "特殊要求")
    private String specialRequests;
} 