package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "商家房源DTO")
public class MerchantHouseDTO {

    @Schema(description = "房源名称")
    private String name;

    @Schema(description = "房源类型")
    private String type;

    @Schema(description = "房源地址")
    private String address;

    @Schema(description = "价格/晚")
    private BigDecimal price;

    @Schema(description = "房源描述")
    private String description;

    @Schema(description = "设施列表")
    private List<String> facilities;

    @Schema(description = "图片列表")
    private List<String> images;
} 