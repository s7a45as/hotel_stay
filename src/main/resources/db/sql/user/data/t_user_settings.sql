-- 插入用户设置测试数据
INSERT INTO `t_user_settings` (
    `user_id`, `email_notification`, `sms_notification`,
    `order_update_notification`, `promotion_notification`,
    `language`, `currency`, `timezone`
) VALUES
(1, 1, 1, 1, 1, 'zh_CN', 'CNY', 'Asia/Shanghai'),
(2, 1, 0, 1, 0, 'en_US', 'USD', 'America/New_York'),
(3, 0, 1, 1, 0, 'zh_CN', 'CNY', 'Asia/Shanghai'); 