package com.homestay.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_merchant")
public class AuthMerchant extends BaseUser {
    
    private String businessName;
    
    private String businessLicense;
    
    private String businessAddress;
    
    private String contactPerson;
    
    private String auditRemark;
} 