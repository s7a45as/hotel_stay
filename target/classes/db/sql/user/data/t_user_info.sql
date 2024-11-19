-- 插入用户测试数据
INSERT INTO `t_user_info` (
    `username`, `password`, `nickname`, `phone`, 
    `email`, `gender`, `birthday`, `address`, 
    `bio`, `role`, `status`
) VALUES
('user1', '{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuR1wuQI0QzLXx.tMqo1.H8Yvq2vPW', '张三', '13800138001',
 'user1@example.com', '男', '1990-01-01', '北京市朝阳区',
 '热爱旅行的美食达人', 'USER', 1),
 
('user2', '{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuR1wuQI0QzLXx.tMqo1.H8Yvq2vPW', '李四', '13800138002',
 'user2@example.com', '女', '1992-02-02', '上海市浦东新区',
 '摄影爱好者', 'USER', 1),
 
('user3', '{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuR1wuQI0QzLXx.tMqo1.H8Yvq2vPW', '王五', '13800138003',
 'user3@example.com', '男', '1988-03-03', '广州市天河区',
 '背包客', 'USER', 1); 