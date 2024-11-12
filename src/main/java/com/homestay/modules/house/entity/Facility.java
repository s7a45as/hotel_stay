package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("house_facility")
@Schema(description = "房源设施信息")
public class Facility {

    @TableId(type = IdType.AUTO)
    @Schema(description = "设施ID")
    private Long id;

    @Schema(description = "设施名称")
    private String name;

    @Schema(description = "设施图标")
    private String icon;

    @Schema(description = "设施描述")
    private String description;
} 