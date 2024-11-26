package com.homestay.modules.merchant.dto;

import com.homestay.modules.order.dto.OrderDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description="用于商家订单详情")

public class MerchantOrderDetailDTO {

    @Schema(description = "订单ID")
    private String id;

    @Schema(description = "订单状态")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "订单金额")
    private BigDecimal amount;

    @Schema(description = "房源信息")
    private OrderDetailDTO.HouseInfo house;

    @Schema(description = "入住日期")
    private LocalDate checkIn;

    @Schema(description = "退房日期")
    private LocalDate checkOut;

    @Schema(description = "入住天数")
    private Integer nights;

    @Schema(description = "入住人数")
    private Integer guests;

    @Schema(description = "联系人姓名")
    private String contactName;

    @Schema(description = "联系人电话")
    private String contactPhone;

    @Schema(description = "特殊要求")
    private String specialRequests;

    @Data
    @Schema(description = "房源信息")
    public static class HouseInfo {
        @Schema(description = "房源ID")
        private Long id;

        @Schema(description = "房源名称")
        private String name;

        @Schema(description = "房源图片")
        private String image;

        @Schema(description = "房源地址")
        private String address;
    }
}
