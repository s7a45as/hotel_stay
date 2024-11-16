package com.homestay.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "热门目的地")
@TableName("t_destination")
public class Destination implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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
    
    @Schema(description = "状态")
    private Integer status;
} 