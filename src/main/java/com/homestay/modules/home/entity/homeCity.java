package com.homestay.modules.home.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "城市信息")
public class homeCity {
    
    @Schema(description = "城市代码")
    private String value;
    
    @Schema(description = "城市名称")
    private String label;
} 