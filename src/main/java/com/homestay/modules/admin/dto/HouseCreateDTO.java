package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "创建房源DTO")
public class HouseCreateDTO {
    
    @NotBlank(message = "房源名称不能为空")
    @Size(max = 100, message = "房源名称最大长度为100")
    @Schema(description = "房源名称")
    private String name;
    
    @NotBlank(message = "房源类型不能为空")
    @Schema(description = "房源类型")
    private String type;
    
    @NotNull(message = "价格不能为空")
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
} 