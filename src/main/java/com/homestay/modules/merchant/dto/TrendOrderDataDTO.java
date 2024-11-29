package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Schema(description = "订单趋势数据")
public class TrendOrderDataDTO {

    @Schema(description = "年份列表")
    List<String> years;


    @Schema(description = "月份列表")
    private List<String> months;

    @Schema(description = "数据列表")
    private List<Long> data;
}
