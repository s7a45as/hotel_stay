package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "商家审核DTO")
public class MerchantAuditDTO {
    
    @Schema(description = "审核结果(1:通过 2:拒绝)")
    @NotNull(message = "审核结果不能为空")
    private Integer result;
    
    @Schema(description = "审核备注")
    private String remark;
} 