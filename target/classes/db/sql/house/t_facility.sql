CREATE TABLE `t_facility` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设施ID',
  `name` varchar(50) NOT NULL COMMENT '设施名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '设施图标',
  `description` varchar(255) DEFAULT NULL COMMENT '设施描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设施信息表'; 