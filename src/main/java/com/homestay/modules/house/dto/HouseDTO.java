package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "房源信息数据传输对象")
public class HouseDTO {
    
    @Schema(description = "房源ID")
    private Long id;
    
    @Schema(description = "房源名称")
    private String name;
    
    @Schema(description = "房源描述")
    private String description;
    
    @Schema(description = "房源类型")
    private String type;
    
    @Schema(description = "房源地址")
    private String address;
    
    @Schema(description = "价格/晚")
    private BigDecimal price;
    
    @Schema(description = "房源图片")
    private String image;
    
    @Schema(description = "房东ID")
    private Long merchantId;
    
    @Schema(description = "房东名称")
    private String merchantName;
    
    @Schema(description = "状态(0:下架 1:上架)")
    private Integer status;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 