package com.homestay.modules.merchant.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(0, "待支付", "PENDING"),
    PAID(1, "已支付", "PAID"),
    CANCELLED(2, "已取消", "CANCELLED"),
    COMPLETED(3, "已完成", "COMPLETED"),
    REFUNDING(4, "退款中", "REFUNDING"),
    REFUNDED(5, "已退款", "REFUNDED");

    private final Integer code;
    private final String desc;
    private final String value;

    OrderStatus(Integer code, String desc, String value) {
        this.code = code;
        this.desc = desc;
        this.value = value;
    }

    public static OrderStatus getByCode(Integer code) {
        for (OrderStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return PENDING;
    }

    public static OrderStatus getByValue(String value) {
        for (OrderStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return PENDING;
    }
} 