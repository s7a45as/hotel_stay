package com.homestay.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "地区信息")
@TableName("t_districts")  // 映射到数据库表 t_districts
public class homeDistricts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "地区ID")
    @TableField("id")  // 映射到数据库的 id 字段
    private Long id;

    @Schema(description = "地区编码")
    @TableField("code")  // 映射到数据库的 code 字段
    private String code;

    @Schema(description = "地区名称")
    @TableField("name")  // 映射到数据库的 name 字段
    private String name;

    @Schema(description = "所属城市编码")
    @TableField("city_code")  // 映射到数据库的 city_code 字段
    private String cityCode;

    @Schema(description = "创建时间")
    @TableField("created_at")  // 映射到数据库的 created_at 字段
    private String createdAt;

    @Schema(description = "更新时间")
    @TableField("updated_at")  // 映射到数据库的 updated_at 字段
    private String updatedAt;

    @Schema(description = "状态")
    @TableField("status")  // 映射到数据库的 status 字段
    private Integer status;

    @Schema(description = "图片")
    @TableField("image")  // 假设存在 image 字段
    private String image;

    @Schema(description = "描述")
    @TableField("description")  // 假设存在 description 字段
    private String description;
}
