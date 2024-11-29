package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "商家房源DTO")
public class MerchantHouseDTO {

    @Schema(description = "房源ID")
    private Long id;

    @Schema(description = "房源标题")
    private String title;

    @Schema(description = "房源名称")
    private String name;

    @Schema(description = "房源类型")
    private String type;

    @Schema(description = "房源分类")
    private String category;

    @Schema(description = "所在城市")
    private String city;

    @Schema(description = "所在地区")
    private String district;

    @Schema(description = "详细位置")
    private String location;

    @Schema(description = "完整地址")
    private String address;

    @Schema(description = "价格/晚")
    @DecimalMin(value = "0", message = "价格必须大于0")
    private BigDecimal price;

    @Schema(description = "最大入住人数")
    @Min(value = 1, message = "最少可住1人")
    private Integer maxGuests;

    @Schema(description = "房源描述")
    @Size(max = 500, message = "描述不能超过500字")
    private String description;

    @Schema(description = "房源状态 0-下架 1-上架 2-审核中")
    private Integer status;

    @Schema(description = "设施列表")
    private List<String> facilities;

    @Schema(description = "特色标签")
    private List<String> features;

    @Schema(description = "图片列表")
    private List<String> images;

    @Schema(description = "主图")
    private String image;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description = "商家名称")
    private String merchantName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}