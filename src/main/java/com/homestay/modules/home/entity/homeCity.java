package com.homestay.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "城市信息")
@TableName("t_homecity")
public class homeCity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "城市代码")
    private String value;
    
    @Schema(description = "城市名称")
    private String label;
    
    @Schema(description = "状态")
    private Integer status;
} 