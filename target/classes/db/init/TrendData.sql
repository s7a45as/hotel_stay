-- 趋势数据表
CREATE TABLE `t_trend_data` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `type` varchar(20) NOT NULL COMMENT '数据类型(USER:用户趋势 ORDER:订单趋势)',
                                `date` date NOT NULL COMMENT '统计日期',
                                `count` int NOT NULL COMMENT '数量',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`),
                                KEY `idx_type_date` (`type`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='趋势数据表';