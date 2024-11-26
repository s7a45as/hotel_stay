CREATE TABLE `t_merchant_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `title` varchar(100) NOT NULL COMMENT '消息标题',
  `content` text NOT NULL COMMENT '消息内容',
  `type` varchar(20) NOT NULL COMMENT '消息类型',
  `priority` varchar(10) NOT NULL DEFAULT 'normal' COMMENT '优先级',
  `sender` varchar(50) NOT NULL COMMENT '发送者',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_type` (`type`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家消息表';
