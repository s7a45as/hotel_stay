CREATE TABLE `t_house_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `url` varchar(255) NOT NULL COMMENT '图片URL',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '图片类型(0:普通图片 1:封面图)',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='房源图片表'; 