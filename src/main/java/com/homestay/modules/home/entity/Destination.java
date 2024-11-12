package com.homestay.modules.home.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "热门目的地")
public class Destination {
    
    @Schema(description = "目的地ID")
    private Long id;
    
    @Schema(description = "目的地名称")
    private String name;
    
    @Schema(description = "城市代码")
    private String cityCode;
    
    @Schema(description = "图片")
    private String image;
    
    @Schema(description = "描述")
    private String description;
} 