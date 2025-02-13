package com.homestay.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "城市信息")
@TableName("t_cities")  // 映射到数据库表 t_cities
public class homeCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "城市ID")
    @TableField("id")  // 映射到数据库的 id 字段
    private Long id;

    @Schema(description = "城市编码")
    @TableField("code")  // 映射到数据库的 code 字段
    private String code;

    @Schema(description = "城市名称")
    @TableField("name")  // 映射到数据库的 name 字段
    private String name;

    @Schema(description = "行政级别：1-省份，2-直辖市")
    @TableField("level")  // 映射到数据库的 level 字段
    private Integer level;

    @Schema(description = "创建时间")
    @TableField("created_at")  // 映射到数据库的 created_at 字段
    private String createdAt;

    @Schema(description = "更新时间")
    @TableField("updated_at")  // 映射到数据库的 updated_at 字段
    private String updatedAt;

    @Schema(description = "状态")  // 假设这里是额外的字段，不在数据库表中
    private Integer status;
}
