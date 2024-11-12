package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_house_image")
@Schema(description = "房源图片")
public class HouseImage {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "图片ID")
    private Long id;
    
    @Schema(description = "房源ID")
    private Long houseId;
    
    @Schema(description = "图片URL")
    private String url;
    
    @Schema(description = "图片类型(0:普通图片 1:封面图)")
    private Integer type;
    
    @Schema(description = "排序")
    private Integer sort;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @Version
    private Integer version;
    
    @TableLogic
    private Integer deleted;
} 