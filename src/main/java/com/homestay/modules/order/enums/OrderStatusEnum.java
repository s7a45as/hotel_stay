package com.homestay.modules.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    PENDING_PAYMENT(0, "待支付"),
    PAID(1, "已支付"),
    CANCELLED(2, "已取消"),
    COMPLETED(3, "已完成"),
    UNKNOWN(-1, "未知状态");

    private final Integer code;
    private final String description;

    OrderStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderStatusEnum fromCode(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return UNKNOWN;
    }

    public static String getDescriptionByCode(Integer code) {
        return fromCode(code).getDescription();
    }
} 