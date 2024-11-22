package com.homestay.common.shareentity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "房源信息")
@TableName(value = "t_house")
public class House {

    @TableId(type = IdType.AUTO)
    @Schema(description = "房源ID")
    private Long id;

    @Schema(description = "房源标题")
    private String title;

    @Schema(description = "房源名称")
    private String name;

    @Schema(description = "房源类型")
    private String type;

    @Schema(description = "房源地址")
    private String address;

    @Schema(description = "所在城市")
    private String city;

    @Schema(description = "所在地区")
    private String district;

    @Schema(description = "详细位置")
    private String location;

    @Schema(description = "价格/晚")
    private BigDecimal price;

    @Schema(description = "最大入住人数")
    private Integer maxGuests;

    @Schema(description = "房源描述")
    private String description;

    @Schema(description = "房源分类")
    private String category;

    @Schema(description = "状态(0:下架 1:上架)")
    private Integer status;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description = "商家名称")
    private String merchantName;

    @Schema(description = "设施列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> facilities;

    @Schema(description = "图片列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> images;

    @Schema(description = "评分")
    private Double rating;

    @Schema(description = "评价数量")
    private Integer reviewCount;

    @Schema(description = "特色标签")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> features;

    @Schema(description = "房源图片")
    private String image;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer deleted;

} 