package com.homestay.modules.merchant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HouseTypeEnum {
    SINGLE_ROOM("单人间", "单人间"),
    DOUBLE_ROOM("双人间", "双人间"),
    SUITE("套房", "套房"),
    VILLA("别墅", "别墅");

    private final String code;
    private final String desc;

    public static boolean contains(String code) {
        return Arrays.stream(values())
                .anyMatch(type -> type.getCode().equals(code));
    }
}