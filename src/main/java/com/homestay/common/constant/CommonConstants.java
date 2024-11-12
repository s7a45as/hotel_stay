package com.homestay.common.constant;

/**
 * 系统常量
 */
public interface CommonConstants {
    
    /**
     * Redis相关常量
     */
    interface Redis {
        /** 验证码前缀 */
        String VERIFY_CODE_PREFIX = "verify_code:";
        /** 验证码过期时间（秒） */
        long VERIFY_CODE_EXPIRE = 300L; // 5分钟
        /** Token前缀 */
        String TOKEN_PREFIX = "token:";
        /** Token过期时间（秒） */
        long TOKEN_EXPIRE = 604800L; // 7天
    }

    /**
     * 系统常量
     */
    interface System {
        /** 正常状态 */
        int NORMAL_STATUS = 1;
        /** 禁用状态 */
        int DISABLED_STATUS = 0;
        /** 默认角色 */
        String DEFAULT_ROLE = "user";
        /** 管理员角色 */
        String ADMIN_ROLE = "admin";
        /** 商家角色 */
        String MERCHANT_ROLE = "merchant";
        /** 待审核状态 */
        int PENDING_STATUS = 2;
        /** 管理员邮箱 */
        String ADMIN_EMAIL = "457325090@qq.com";
    }

    /**
     * 安全相关常量
     */
    interface Security {
        /** Token前缀 */
        String TOKEN_PREFIX = "Bearer ";
        /** Token请求头 */
        String AUTHORIZATION = "Authorization";
        /** 密码加密盐值 */
        String PASSWORD_SALT = "homestay";
    }

    /**
     * 业务相关常量
     */
    interface Business {
        /** 默认页码 */
        int DEFAULT_PAGE_NUM = 1;
        /** 默认每页数量 */
        int DEFAULT_PAGE_SIZE = 10;
        /** 最大每页数量 */
        int MAX_PAGE_SIZE = 100;
    }

    /**
     * 结果码常量
     */
    interface ResultCode {
        /** 营业执照已存在 */
        int BUSINESS_LICENSE_EXIST = 2010;
    }
} 