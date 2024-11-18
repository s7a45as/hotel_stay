CREATE TABLE `t_user_privacy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `profile_visibility` varchar(20) NOT NULL DEFAULT 'PUBLIC' COMMENT '个人资料可见性(PUBLIC:公开 FRIENDS:好友可见 PRIVATE:私密)',
  `show_online_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '显示在线状态',
  `show_last_seen` tinyint(1) NOT NULL DEFAULT '1' COMMENT '显示最后在线时间',
  `allow_friend_requests` tinyint(1) NOT NULL DEFAULT '1' COMMENT '允许好友请求',
  `allow_messages` tinyint(1) NOT NULL DEFAULT '1' COMMENT '允许陌生人消息',
  `show_email` tinyint(1) NOT NULL DEFAULT '0' COMMENT '显示邮箱',
  `show_phone` tinyint(1) NOT NULL DEFAULT '0' COMMENT '显示手机号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户隐私设置表'; 