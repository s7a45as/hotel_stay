package com.homestay.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_trend_data")
public class TrendData {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String type;
    
    private LocalDate date;
    
    private Integer count;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 