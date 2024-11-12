package com.homestay.modules.payment.constant;

public interface PaymentConstants {
    
    /**
     * 支付状态
     */
    interface Status {
        /** 待支付 */
        String PENDING = "PENDING";
        /** 已支付 */
        String PAID = "PAID";
        /** 已取消 */
        String CANCELLED = "CANCELLED";
    }
    
    /**
     * 支付方式
     */
    interface Method {
        /** 微信支付 */
        String WECHAT = "wechat";
        /** 支付宝 */
        String ALIPAY = "alipay";
    }
} 