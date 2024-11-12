package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "房源查询参数")
public class HouseQueryDTO {
    
    @Schema(description = "页码")
    private Integer page = 1;
    
    @Schema(description = "每页数量")
    private Integer pageSize = 12;
    
    @Schema(description = "房型ID")
    private String categoryId;
    
    @Schema(description = "位置")
    private String location;
    
    @Schema(description = "价格范围")
    private Integer[] priceRange;
    
    @Schema(description = "设施")
    private String[] facilities;
    
    @Schema(description = "排序方式")
    private String sortBy;
} 