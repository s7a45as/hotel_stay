package com.homestay.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_admin") // 对应表名
@Schema(description = "管理员信息")
public class AdminUser  extends BaseUser{

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

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "状态(0:禁用 1:正常)")
    private Integer status;

    @Schema(description = "审核备注")
    private String remark;

    @Schema(description = "部门")
    private String department;

    @Schema(description = "职位")
    private String position;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "是否删除(0:未删除 1:已删除)")
    private Integer deleted;
}
