CREATE TABLE `t_refund` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款ID',
  `payment_id` bigint NOT NULL COMMENT '支付ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `amount` decimal(10,2) NOT NULL COMMENT '退款金额',
  `reason` varchar(255) NOT NULL COMMENT '退款原因',
  `status` varchar(20) NOT NULL COMMENT '退款状态(PENDING-处理中 SUCCESS-成功 FAILED-失败)',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_payment_id` (`payment_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款信息表'; 