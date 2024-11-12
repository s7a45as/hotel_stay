package com.homestay.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class NormalUser extends BaseUser {
    // 可以添加普通用户特有的字段
    private String interests;
    private String preferences;
} 