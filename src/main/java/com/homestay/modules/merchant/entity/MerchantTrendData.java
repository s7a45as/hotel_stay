package com.homestay.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName(value = "t_merchant_trend_data",autoResultMap = true)
@Schema(description = "商家趋势数据")
public class MerchantTrendData {

    @TableId(type = IdType.AUTO)
    @Schema(description = "数据id")
    private Long id;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description = "年份")
    private String years;

    @Schema(description = "月份")
    private String months;

    @Schema(description = "订单数")
    private Long AllOrder;

    @Schema(description = "收入总数")
    private BigDecimal allPrice;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
