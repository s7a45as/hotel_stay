package com.homestay.common.constant;

public interface BookingConstants {
    
    /**
     * 预订状态
     */
    interface Status {
        /** 待支付 */
        int PENDING_PAYMENT = 0;
        /** 已支付 */
        int PAID = 1;
        /** 已取消 */
        int CANCELLED = 2;
        /** 已完成 */
        int COMPLETED = 3;
    }
    
    /**
     * 业务规则
     */
    interface Rules {
        /** 最大提前预订天数 */
        int MAX_ADVANCE_DAYS = 180;
        /** 最小提前预订小时数 */
        int MIN_ADVANCE_HOURS = 24;
        /** 最大连续预订天数 */
        int MAX_BOOKING_DAYS = 30;
    }
} 