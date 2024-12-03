package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "商家审核DTO")
public class MerchantAuditDTO {
    
    @NotBlank(message = "审核状态不能为空")
    @Schema(description = "审核状态(APPROVED-通过/REJECTED-拒绝)")
    private String status;
    
    @Schema(description = "审核原因")
    private String reason;
} 