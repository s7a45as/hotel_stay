-- 创建普通用户表
CREATE TABLE `sys_user` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                            `username` VARCHAR(50) NOT NULL COMMENT '用户名',
                            `password` VARCHAR(100) NOT NULL COMMENT '密码',
                            `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
                            `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
                            `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
                            `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                            `gender` VARCHAR(100) DEFAULT NULL COMMENT '性别(M:男 F:女 O:其他)',
                            `birthday` DATETIME DEFAULT NULL COMMENT '生日',
                            `address` VARCHAR(255) DEFAULT NULL COMMENT '居住地址',
                            `bio` TEXT DEFAULT NULL COMMENT '个人简介',
                            `interests` VARCHAR(255) DEFAULT NULL COMMENT '兴趣爱好',
                            `preferences` VARCHAR(255) DEFAULT NULL COMMENT '偏好设置',
                            `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0:禁用 1:正常)',
                            `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
                            `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
                            `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `version` INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
                            `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_username` (`username`),
                            UNIQUE KEY `uk_email` (`email`),
                            UNIQUE KEY `uk_phone` (`phone`),
                            KEY `idx_status` (`status`),
                            KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';


-- 创建商家表
CREATE TABLE IF NOT EXISTS `sys_merchant` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商家ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(255) COMMENT '头像URL',
    `status` TINYINT NOT NULL DEFAULT 2 COMMENT '状态(0:禁用 1:正常 2:待审核)',
    `business_name` VARCHAR(100) NOT NULL COMMENT '商家名称',
    `business_license` VARCHAR(50) NOT NULL COMMENT '营业执照号',
    `business_address` VARCHAR(255) NOT NULL COMMENT '商家地址',
    `contact_person` VARCHAR(50) NOT NULL COMMENT '联系人',
    `audit_remark` VARCHAR(255) COMMENT '审核备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_business_license` (`business_license`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家表';

-- 创建管理员表
CREATE TABLE IF NOT EXISTS `sys_admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(255) COMMENT '头像URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0:禁用 1:正常)',
    `department` VARCHAR(50) COMMENT '部门',
    `position` VARCHAR(50) COMMENT '职位',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表'; 