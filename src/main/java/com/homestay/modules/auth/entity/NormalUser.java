package com.homestay.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Schema(description = "普通用户实体")
public class NormalUser extends BaseUser {
    
    @Schema(description = "兴趣爱好")
    private String interests;
    
    @Schema(description = "偏好设置")
    private String preferences;
    
    @Schema(description = "性别(M:男 F:女 O:其他)")
    private String gender;
    
    @Schema(description = "生日")
    private LocalDateTime birthday;
    
    @Schema(description = "居住地址")
    private String address;
    
    @Schema(description = "个人简介")
    private String bio;
    
    @Schema(description = "头像URL")
    private String avatar;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @TableLogic
    @Schema(description = "是否删除(0:未删除 1:已删除)")
    private Integer deleted;
    
    @Schema(description = "状态(0:禁用 1:正常)")
    private Integer status;
    
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;
    
    @Schema(description = "最后登录IP")
    private String lastLoginIp;
    
    @Version
    @Schema(description = "乐观锁版本号")
    private Integer version;
} 