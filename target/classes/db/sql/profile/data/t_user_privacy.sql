-- 插入用户隐私设置测试数据
INSERT INTO `t_user_privacy` (
    `user_id`, `profile_visibility`, `show_online_status`, 
    `show_last_seen`, `allow_friend_requests`, `allow_messages`,
    `show_email`, `show_phone`
) VALUES
-- 普通用户的隐私设置
(1, 'PUBLIC', 1, 1, 1, 1, 0, 0),
(2, 'FRIENDS', 1, 0, 1, 1, 0, 0),
(3, 'PRIVATE', 0, 0, 0, 0, 0, 0),

-- VIP用户的隐私设置
(4, 'PUBLIC', 1, 1, 1, 1, 1, 0),
(5, 'FRIENDS', 1, 1, 1, 1, 0, 0); 