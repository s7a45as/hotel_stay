package com.homestay.modules.home.dto;

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
    
    @Schema(description = "总数")
    private Long total;
} 