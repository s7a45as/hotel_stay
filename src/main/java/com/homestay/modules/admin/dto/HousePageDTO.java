package com.homestay.modules.admin.dto;

import com.homestay.common.shareentity.House;
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
@Schema(description = "房源分页数据")
public class HousePageDTO {
    
    @Schema(description = "房源列表")
    private List<House> list;
    
    @Schema(description = "总记录数")
    private Long total;
    
    @Schema(description = "当前页码")
    private Integer currentPage;
    
    @Schema(description = "每页大小")
    private Integer pageSize;
} 