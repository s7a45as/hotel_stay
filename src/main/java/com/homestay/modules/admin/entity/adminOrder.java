package com.homestay.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@TableName("t_all_order")
public class adminOrder {
    
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    private String houseName;
    
    private String userName;
    
    private String phone;
    
    private LocalDate checkIn;
    
    private LocalDate checkOut;
    
    private Integer nights;
    
    private Integer guests;
    
    private BigDecimal totalAmount;
    
    private String status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    private LocalDateTime payTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
} 