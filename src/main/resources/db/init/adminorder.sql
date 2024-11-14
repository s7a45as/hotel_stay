-- 订单表
CREATE TABLE `t_order` (
                           `id` varchar(32) NOT NULL COMMENT '订单ID',
                           `house_name` varchar(100) NOT NULL COMMENT '房源名称',
                           `user_name` varchar(50) NOT NULL COMMENT '用户姓名',
                           `phone` varchar(20) NOT NULL COMMENT '联系电话',
                           `check_in` date NOT NULL COMMENT '入住日期',
                           `check_out` date NOT NULL COMMENT '离店日期',
                           `nights` int NOT NULL COMMENT '入住天数',
                           `guests` int NOT NULL COMMENT '入住人数',
                           `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
                           `status` varchar(20) NOT NULL COMMENT '订单状态',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
                           PRIMARY KEY (`id`),
                           KEY `idx_user_name` (`user_name`),
                           KEY `idx_status` (`status`),
                           KEY `idx_create_time` (`create_time`),
                           KEY `idx_check_in` (`check_in`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 插入测试数据
INSERT INTO `t_order` (
    `id`, `house_name`, `user_name`, `phone`,
    `check_in`, `check_out`, `nights`, `guests`,
    `total_amount`, `status`, `pay_time`
) VALUES
-- 已支付的订单
(
    'ORD202401010001', '椰梦长廊海景公寓', '张三', '13800138001',
    '2024-01-01', '2024-01-03', 2, 2,
    1376.00, 'PAID', '2024-01-01 10:30:00'
),
(
    'ORD202401010002', '外滩江景套房', '李四', '13800138002',
    '2024-01-05', '2024-01-07', 2, 3,
    2576.00, 'PAID', '2024-01-01 14:20:00'
),

-- 待支付的订单
(
    'ORD202401020001', '故宫旁四合院', '王五', '13800138003',
    '2024-01-10', '2024-01-12', 2, 4,
    3976.00, 'PENDING', NULL
),
(
    'ORD202401020002', '宽窄巷子民宿', '赵六', '13800138004',
    '2024-01-15', '2024-01-16', 1, 2,
    468.00, 'PENDING', NULL
),

-- 已取消的订单
(
    'ORD202401030001', '西湖湖景别墅', '孙七', '13800138005',
    '2024-01-20', '2024-01-22', 2, 4,
    4176.00, 'CANCELLED', NULL
),

-- 已完成的订单
(
    'ORD202312010001', '亚龙湾温泉别墅', '周八', '13800138006',
    '2023-12-24', '2023-12-26', 2, 6,
    5176.00, 'COMPLETED', '2023-12-23 16:40:00'
),
(
    'ORD202312010002', '三里屯SOHO公寓', '吴九', '13800138007',
    '2023-12-25', '2023-12-28', 3, 2,
    2397.00, 'COMPLETED', '2023-12-24 09:15:00'
),

-- 已退款的订单
(
    'ORD202312020001', '钱塘江观景公寓', '郑十', '13800138008',
    '2023-12-30', '2024-01-01', 2, 2,
    1376.00, 'REFUNDED', '2023-12-29 11:20:00'
);