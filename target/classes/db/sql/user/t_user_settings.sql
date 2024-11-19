CREATE TABLE `t_user_settings` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `email_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '邮件通知',
  `sms_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '短信通知',
  `order_update_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '订单更新通知',
  `promotion_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '促销通知',
  `language` varchar(10) NOT NULL DEFAULT 'zh_CN' COMMENT '语言',
  `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '货币',
  `timezone` varchar(50) NOT NULL DEFAULT 'Asia/Shanghai' COMMENT '时区',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户设置表'; 