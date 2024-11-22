package com.homestay.modules.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "房源信息")
public class HouseInfo {
    @Schema(description = "房源ID")
    private Long id;
    
    @Schema(description = "房源名称")
    private String name;
    
    @Schema(description = "房源图片")
    private String image;
    
    @Schema(description = "房源地址")
    private String address;
}