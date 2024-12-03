package com.homestay.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_merchant")
@Schema(description = "商家信息")
public class SysMerchant {
    
    @TableId(type = IdType.AUTO)
    @Schema(description = "商家ID")
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
    
    @Schema(description = "头像URL")
    private String avatar;
    
    @Schema(description = "状态(0:禁用 1:正常 2:待审核)")
    private Integer status;
    
    @Schema(description = "商家名称")
    private String businessName;
    
    @Schema(description = "营业执照号")
    private String businessLicense;
    
    @Schema(description = "商家地址")
    private String businessAddress;
    
    @Schema(description = "联系人")
    private String contactPerson;
    
    @Schema(description = "审核备注")
    private String auditRemark;
    
    @Schema(description = "身份证信息")
    private String idCard;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "是否删除")
    private Integer deleted;
} 