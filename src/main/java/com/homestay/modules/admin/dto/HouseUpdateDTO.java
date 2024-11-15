package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "更新房源DTO")
public class HouseUpdateDTO {
    
    @Size(max = 100, message = "房源名称最大长度为100")
    @Schema(description = "房源名称")
    private String name;
    
    @Schema(description = "房源类型")
    private String type;
    
    @Schema(description = "价格")
    private Integer price;
    
    @Schema(description = "描述")
    private String description;
    
    @Schema(description = "地址")
    private String address;
    
    @Schema(description = "图片列表")
    private List<String> images;
    
    @Schema(description = "设施列表")
    private List<String> facilities;
    
    @Schema(description = "状态")
    private Integer status;
} 