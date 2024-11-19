-- 插入用户隐私设置测试数据
INSERT INTO `t_user_privacy` (
    `user_id`, `profile_visibility`, `show_online_status`,
    `show_last_seen`, `allow_friend_requests`, `allow_messages`,
    `show_email`, `show_phone`
) VALUES
(1, 'PUBLIC', 1, 1, 1, 1, 0, 0),
(2, 'FRIENDS', 1, 0, 1, 1, 0, 0),
(3, 'PRIVATE', 0, 0, 0, 1, 0, 0); 