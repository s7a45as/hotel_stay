CREATE TABLE `t_merchant_promotion` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `name` varchar(100) NOT NULL COMMENT '活动名称',
  `type` varchar(20) NOT NULL COMMENT '活动类型',
  `discount` decimal(10,2) NOT NULL COMMENT '优惠力度',
  `threshold` decimal(10,2) DEFAULT NULL COMMENT '使用门槛',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `status` tinyint NOT NULL COMMENT '活动状态',
  `description` varchar(500) DEFAULT NULL COMMENT '活动描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家优惠活动表';



