package com.homestay.modules.merchant.enums;



import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 商家状态枚举
 */
@Getter
@AllArgsConstructor
public enum MerchantStatusEnum {

    PENDING(0, "待审核", "商家信息待审核"),
    NORMAL(1, "正常", "商家正常营业"),
    REJECTED(2, "审核拒绝", "商家审核未通过"),
    SUSPENDED(3, "已暂停", "商家暂停营业"),
    BLACKLISTED(4, "已拉黑", "商家被加入黑名单"),
    DELETED(5, "已注销", "商家已注销账号");

    private final Integer code;
    private final String name;
    private final String description;

    /**
     * 判断状态码是否有效
     */
    public static boolean isValid(Integer code) {
        if (code == null) {
            return false;
        }
        for (MerchantStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据状态码获取枚举
     */
    public static MerchantStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MerchantStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断商家是否可以正常经营
     */
    public static boolean canOperate(Integer code) {
        return NORMAL.getCode().equals(code);
    }

    /**
     * 获取状态描述
     */
    public static String getDescription(Integer code) {
        MerchantStatusEnum status = getByCode(code);
        return status == null ? "未知状态" : status.getDescription();
    }

    /**
     * 获取状态名称
     */
    public static String getName(Integer code) {
        MerchantStatusEnum status = getByCode(code);
        return status == null ? "未知" : status.getName();
    }
}