-- 管理员表
CREATE TABLE `t_admin` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
                           `username` varchar(50) NOT NULL COMMENT '用户名',
                           `password` varchar(100) NOT NULL COMMENT '密码',
                           `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
                           `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                           `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                           `department` varchar(50) DEFAULT NULL COMMENT '部门',
                           `position` varchar(50) DEFAULT NULL COMMENT '职位',
                           `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0:待审核 1:正常 2:禁用)',
                           `remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uk_username` (`username`),
                           KEY `idx_status` (`status`),
                           KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- 插入测试数据
INSERT INTO `t_admin` (username, password, nickname, email, phone, department, position, status) VALUES
                                                                                                     ('admin1', '{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuR1wuQI0QzLXx.tMqo1.H8Yvq2vPW', '张管理', 'admin1@example.com', '13800138001', '运营部', '运营经理', 1),
                                                                                                     ('admin2', '{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuQI0QzLXx.tMqo1.H8Yvq2vPW', '李运营', 'admin2@example.com', '13800138002', '客服部', '客服主管', 0),
                                                                                                     ('admin3', '{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuQI0QzLXx.tMqo1.H8Yvq2vPW', '王客服', 'admin3@example.com', '13800138003', '技术部', '技术经理', 0);