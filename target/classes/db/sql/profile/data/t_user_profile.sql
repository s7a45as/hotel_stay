-- 插入用户个人信息测试数据
INSERT INTO `t_user_profile` (
    `user_id`, `nickname`, `avatar`, `gender`, `birthday`, 
    `phone`, `email`, `address`, `bio`, `interests`, 
    `occupation`, `education`
) VALUES
-- 普通用户
(1, '张三', 'https://example.com/avatars/zhangsan.jpg', '男', '1990-01-01',
 '13800138001', 'zhangsan@example.com', '浙江省杭州市西湖区', '热爱旅行的美食达人',
 '["旅行", "美食", "摄影"]', '工程师', '本科'),

(2, '李四', 'https://example.com/avatars/lisi.jpg', '女', '1992-03-15',
 '13800138002', 'lisi@example.com', '江苏省南京市鼓楼区', '独立摄影师，常驻南京',
 '["摄影", "音乐", "阅读"]', '摄影师', '研究生'),

(3, '王五', 'https://example.com/avatars/wangwu.jpg', '男', '1988-07-20',
 '13800138003', 'wangwu@example.com', '广东省深圳市南山区', '科技爱好者，创业中',
 '["科技", "创业", "运动"]', '创业者', '本科'),

-- VIP用户
(4, '赵六', 'https://example.com/avatars/zhaoliu.jpg', '女', '1995-12-25',
 '13800138004', 'zhaoliu@example.com', '上海市浦东新区', '美食评论家，美食杂志专栏作者',
 '["美食", "写作", "品茶"]', '美食评论家', '硕士'),

(5, '钱七', 'https://example.com/avatars/qianqi.jpg', '男', '1985-09-10',
 '13800138005', 'qianqi@example.com', '北京市朝阳区', '资深旅行家，已去过100个国家',
 '["旅行", "冒险", "摄影"]', '旅行作家', '本科'); 