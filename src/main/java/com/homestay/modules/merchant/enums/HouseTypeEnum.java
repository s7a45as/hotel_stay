package com.homestay.modules.merchant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HouseTypeEnum {
    BASE_TYPE("BaseType", "基础类型"),
    RESIDENTIAL("Residential", "住宅类型"),
    SCENIC("Scenic", "景观特色"),
    VILLA_TYPE("VillaType", "别墅类型"),
    SPECIAL("Special", "特色房源"),
    SINGLE_ROOM("SingleRoom", "单人间"),
    DOUBLE_ROOM("DoubleRoom", "双人间"),
    SUITE("Suite", "套房"),
    VILLA("Villa", "别墅");

    private final String code;
    private final String desc;

    /**
     * 判断是否包含某个code
     *
     * @param code 要检查的code
     * @return 是否包含
     */
    public static boolean contains(String code) {
        return Arrays.stream(values())
                .anyMatch(type -> type.getCode().equals(code));
    }

    /**
     * 根据code获取描述
     *
     * @param code 要查询的code
     * @return 对应的描述
     */
    public static String getDescriptionByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .map(HouseTypeEnum::getDesc)
                .findFirst()
                .orElse("未知类型");
    }
}
