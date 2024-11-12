package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "趋势数据")
public class TrendDataDTO {
    
    @Schema(description = "月份列表")
    private List<String> months;
    
    @Schema(description = "数据列表")
    private List<Integer> data;
} 