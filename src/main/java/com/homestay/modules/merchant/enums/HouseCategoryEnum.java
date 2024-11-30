package com.homestay.modules.merchant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter

public enum HouseCategoryEnum {
    BOUTIQUE("精品房源"),
    ECONOMIC("经济型"),
    LUXURY("豪华型"),
    FAMILY("家庭型"),
    BUSINESS("商务型"),
    // 新增房源类型
    VILLA("别墅"),
    APARTMENT("公寓"),
    RESORT("度假村"),
    HOMESTAY("民宿"),
    STUDIO("单间"),
    LOFT("复式"),
    COURTYARD("四合院"),
    SEAVIEW("海景房"),
    MOUNTAIN_VIEW("山景房"),
    LAKE_VIEW("湖景房"),
    GARDEN("花园洋房"),
    TOWNHOUSE("联排别墅"),
    DETACHED("独栋别墅"),
    TRADITIONAL("传统民居"),
    CREATIVE("创意主题"),
    ECO("生态度假"),
    RURAL("乡村农家"),
    BED_BREAKFAST("床和早餐"),
    SHARED_ROOM("合住房间"),
    ENTIRE_HOUSE("整套房源");

    private final String description;

    HouseCategoryEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean contains(String category) {
        try {
            valueOf(category.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}