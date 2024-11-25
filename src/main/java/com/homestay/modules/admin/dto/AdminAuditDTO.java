package com.homestay.modules.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "管理员审核参数")
public class AdminAuditDTO {
    
    @Schema(description = "审核结果", required = true, example = "true", 
           title = "是否通过审核")
    @NotNull(message = "审核结果不能为空")
    private Boolean approved;
    
    @Schema(description = "审核备注", example = "资质符合要求，予以通过", 
           title = "审核意见")
    private String remark;
    
    @Schema(description = "待审核用户ID", hidden = true)
    private Long id;
    
    @Schema(description = "审核状态：0-拒绝，1-通过", hidden = true)
    private Integer status;
} 