package com.homestay.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_admin")
public class AdminUser extends BaseUser {
    private String department;
    private String position;
} 