package com.homestay.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "优惠活动信息")
@TableName("t_sys_promotions")
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "活动ID")
    private Long id;

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "活动描述")
    private String description;

    @Schema(description = "活动类型：DISCOUNT-折扣, COUPON-优惠券")
    private String type;

    @Schema(description = "优惠金额/折扣率")
    private Double value;

    @Schema(description = "活动开始时间")
    private LocalDateTime startTime;

    @Schema(description = "活动结束时间")
    private LocalDateTime endTime;

    @Schema(description = "活动状态：0-未开始，1-进行中，2-已结束")
    private Integer status;

    @Schema(description = "活动图片")
    private String image;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
} 