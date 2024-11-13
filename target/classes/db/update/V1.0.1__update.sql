-- 这里放后续的表结构更新SQL
-- 例如：添加新的字段、修改字段类型、添加索引等

-- 示例：给用户表添加最后登录时间字段
ALTER TABLE `sys_user` 
ADD COLUMN `last_login_time` DATETIME COMMENT '最后登录时间' AFTER `status`;

-- 示例：给商家表添加营业时间字段
ALTER TABLE `sys_merchant`
ADD COLUMN `business_hours` VARCHAR(100) COMMENT '营业时间' AFTER `business_address`; 