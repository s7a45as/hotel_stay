package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "趋势数据")
public class TrendDataDTO {
    
    @Schema(description = "月份列表", example = "['1月', '2月', '3月', '4月', '5月', '6月']",
           title = "最近6个月的月份标签")
    private List<String> months;
    
    @Schema(description = "对应月份的数据", example = "[100, 120, 150, 180, 200, 220]",
           title = "每个月份对应的统计数值")
    private List<Integer> data;
} 