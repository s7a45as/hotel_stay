package com.homestay.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_admin")
@Schema(description = "管理员信息")
public class AdminUser  extends BaseUser {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "管理员ID")
    private Long id;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "密码")
    private String password;
    
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
    
    @Schema(description = "状态(0:待审核 1:正常 2:禁用)")
    private Integer status;
    
    @Schema(description = "审核备注")
    private String remark;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 