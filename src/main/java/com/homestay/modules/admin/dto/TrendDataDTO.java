package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "趋势数据")
public class TrendDataDTO {
    
    @Schema(description = "月份")
    private List<String> months;
    
    @Schema(description = "数据")
    private List<Integer> data;
} 