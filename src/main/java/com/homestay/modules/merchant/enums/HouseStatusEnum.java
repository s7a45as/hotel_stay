package com.homestay.modules.merchant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HouseStatusEnum {
    OFF_SHELF(0, "已下架"),
    ON_SHELF(1, "上架中"),
    REVIEWING(2, "审核中");

    private final Integer code;
    private final String desc;

    public static boolean contains(Integer code) {
        return Arrays.stream(values())
                .anyMatch(status -> status.getCode().equals(code));
    }
}