package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "房源列表数据传输对象")
public class HouseListDTO {
    
    @Schema(description = "总记录数")
    private Long total;
    
    @Schema(description = "当前页码")
    private Integer currentPage;
    
    @Schema(description = "每页大小")
    private Integer pageSize;
    
    @Schema(description = "房源列表")
    private List<HouseDTO> list;
} 