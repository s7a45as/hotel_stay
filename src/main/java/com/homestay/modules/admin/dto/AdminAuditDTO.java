package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "管理员审核参数")
public class AdminAuditDTO {
    
    @Schema(description = "审核结果", required = true)
    @NotNull(message = "审核结果不能为空")
    private Boolean approved;
    
    @Schema(description = "审核备注")
    private String remark;
} 