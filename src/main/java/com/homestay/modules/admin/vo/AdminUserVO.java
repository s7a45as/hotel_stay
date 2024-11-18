package com.homestay.modules.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理员用户信息VO")
public class AdminUserVO {
    
    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "部门")
    private String department;
    
    @Schema(description = "职位")
    private String position;
    
    @Schema(description = "状态")
    private Integer status;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 商家特有字段
    @Schema(description = "商家名称")
    private String businessName;
    
    @Schema(description = "联系人")
    private String contactPerson;
    
    @Schema(description = "营业执照号")
    private String businessLicense;
    
    @Schema(description = "商家地址")
    private String businessAddress;
    
    @Schema(description = "审核备注")
    private String auditRemark;
} 