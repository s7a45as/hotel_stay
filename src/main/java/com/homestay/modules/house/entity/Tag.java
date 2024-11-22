package com.homestay.modules.house.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("tag")
@Schema(description = "标签信息")
public class Tag {

    @TableId(type = IdType.AUTO)
    @Schema(description = "标签ID")
    private Long id;

    @Schema(description = "标签类型")
    private String type;

    @Schema(description = "标签文本")
    private String text;
} 