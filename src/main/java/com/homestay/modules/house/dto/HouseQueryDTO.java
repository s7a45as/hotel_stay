package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "房源查询参数")
public class HouseQueryDTO {
    
    @Schema(description = "页码", example = "1")
    private Integer page = 1;
    
    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;
    
    @Schema(description = "城市")
    private String city;
    
    @Schema(description = "入住人数")
    private Integer guestCount;
    
    @Schema(description = "入住日期")
    private String checkInDate;
    
    @Schema(description = "退房日期")
    private String checkOutDate;
    
    @Schema(description = "最低价格")
    private Integer minPrice;
    
    @Schema(description = "最高价格")
    private Integer maxPrice;
    
    @Schema(description = "房型，多个用逗号分隔")
    private String roomTypes;
    
    @Schema(description = "设施，多个用逗号分隔")
    private String amenities;
    
    @Schema(description = "特色标签，多个用逗号分隔")
    private String tags;
    
    @Schema(description = "地区")
    private String district;
} 