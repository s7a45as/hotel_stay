package com.homestay.modules.merchant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HouseCategoryEnum {
    BOUTIQUE("精品民宿", "精品民宿"),
    CHARACTERISTIC("特色客栈", "特色客栈"),
    THEME("主题公寓", "主题公寓"),
    VACATION("度假别墅", "度假别墅");

    private final String code;
    private final String desc;

    public static boolean contains(String code) {
        return Arrays.stream(values())
                .anyMatch(category -> category.getCode().equals(code));
    }
}