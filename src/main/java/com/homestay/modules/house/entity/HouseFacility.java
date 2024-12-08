package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_house_facility")
@Schema(description = "房源设施关联")
public class HouseFacility {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;
    
    @Schema(description = "房源ID")
    private Long houseId;
    
    @Schema(description = "设施ID")
    private Long facilityId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    @Schema(description = "设施名称")
    private String facilityName;
    
    @TableField(exist = false)
    @Schema(description = "设施描述")
    private String description;
    
    @TableField(exist = false)
    @Schema(description = "设施图标")
    private String icon;
} 