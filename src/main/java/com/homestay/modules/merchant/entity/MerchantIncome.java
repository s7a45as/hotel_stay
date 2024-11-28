package com.homestay.modules.merchant.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName(value = "t_merchant_income",autoResultMap = true)
@Schema(description = "商家总收入")
public class MerchantIncome {
    @TableId(type = IdType.AUTO)
    @Schema(description = "收入表ID")
    private Long id;

    @Schema(description = "商家ID")
    private Long merchantId;

    @Schema(description ="总收入")
    private BigDecimal all_income;


    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
