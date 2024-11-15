CREATE TABLE `t_system_settings` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `site_name` varchar(100) NOT NULL DEFAULT '民宿预订系统' COMMENT '网站名称',
  `site_desc` varchar(255) DEFAULT NULL COMMENT '网站描述',
  `logo` varchar(255) DEFAULT NULL COMMENT 'logo地址',
  `favicon` varchar(255) DEFAULT NULL COMMENT '网站图标地址',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `max_booking_days` int NOT NULL DEFAULT 30 COMMENT '最大预订天数',
  `min_advance_booking_days` int NOT NULL DEFAULT 1 COMMENT '最小提前预订天数',
  `max_advance_booking_days` int NOT NULL DEFAULT 90 COMMENT '最大提前预订天数',
  `check_in_time` varchar(5) NOT NULL DEFAULT '14:00' COMMENT '入住时间',
  `check_out_time` varchar(5) NOT NULL DEFAULT '12:00' COMMENT '退房时间',
  `deposit_rate` int NOT NULL DEFAULT 30 COMMENT '定金比例',
  `cancel_policy` int NOT NULL DEFAULT 1 COMMENT '取消政策',
  `refund_rules` json DEFAULT NULL COMMENT '退款规则',
  `email_notification` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用邮件通知',
  `sms_notification` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用短信通知',
  `notify_before_check_in` int NOT NULL DEFAULT 24 COMMENT '入住前通知时间(小时)',
  `notify_before_check_out` int NOT NULL DEFAULT 2 COMMENT '退房前通知时间(小时)',
  `maintenance` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否维护中',
  `maintenance_message` varchar(255) DEFAULT NULL COMMENT '维护信息',
  `registration_enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开放注册',
  `default_user_role` varchar(20) NOT NULL DEFAULT 'user' COMMENT '默认用户角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统设置表';

-- 插入默认数据
INSERT INTO `t_system_settings` (
  site_name, site_desc, contact_phone, contact_email, address
) VALUES (
  '民宿预订系统', 
  '提供优质的民宿预订服务', 
  '400-123-4567',
  'support@example.com',
  '浙江省杭州市西湖区'
); 