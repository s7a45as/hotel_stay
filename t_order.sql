-- 修改现有表结构以匹配实体类
ALTER TABLE `t_order`
    -- 添加新字段
    ADD COLUMN `order_no` varchar(32) NOT NULL COMMENT '订单编号' AFTER `id`,
    ADD COLUMN `merchant_id` bigint NOT NULL COMMENT '商家ID' AFTER `order_no`,
    ADD COLUMN `house_id` bigint NOT NULL COMMENT '房源ID' AFTER `merchant_id`,
    ADD COLUMN `user_id` bigint NOT NULL COMMENT '用户ID' AFTER `house_name`,
    

    -- 添加索引
    ADD INDEX `idx_order_no` (`order_no`),
    ADD INDEX `idx_merchant_id` (`merchant_id`),
    ADD INDEX `idx_house_id` (`house_id`),
    ADD INDEX `idx_user_id` (`user_id`); 