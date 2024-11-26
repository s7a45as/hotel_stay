-- 插入50条测试数据到商家消息表
INSERT INTO t_merchant_message 
(merchant_id, title, content, type, priority, sender, is_read, create_time) 
VALUES 
-- 订单相关消息
(1001, '新订单通知 #ORDER123', '您有一个新的订单需要处理，订单号：ORDER123', 'ORDER_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '订单已支付 #ORDER124', '订单ORDER124已成功支付，请及时处理', 'ORDER_NOTIFICATION', 'high', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '订单取消提醒 #ORDER125', '订单ORDER125已被用户取消', 'ORDER_NOTIFICATION', 'normal', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 系统通知
(1001, '系统维护通知', '系统将于今晚23:00-次日凌晨2:00进行例行维护', 'SYSTEM_NOTIFICATION', 'high', 'admin', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '功能更新通知', '平台新增商家数据分析功能，点击查看详情', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '账户安全提醒', '请定期更改您的账户密码，确保账户安全', 'SYSTEM_NOTIFICATION', 'low', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 促销活动通知
(1001, '618活动预告', '618购物节即将开始，请提前准备商品促销计划', 'PROMOTION_NOTIFICATION', 'high', 'marketing', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '限时特惠活动', '您的店铺已被选中参加下周限时特惠活动', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '节日营销活动', '中秋节活动报名开始，点击参与', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 评价相关通知
(1001, '新的用户评价', '您收到一条新的用户评价，请及时查看并回复', 'REVIEW_NOTIFICATION', 'normal', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '用户评价提醒', '您有3条用户评价待回复', 'REVIEW_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 账户相关通知
(1001, '账户余额提醒', '您的账户余额已不足1000元，请及时充值', 'ACCOUNT_NOTIFICATION', 'high', 'finance', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '收入结算通知', '上周期订单已完成结算，请查收', 'ACCOUNT_NOTIFICATION', 'normal', 'finance', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 商品相关通知
(1001, '商品库存预警', '商品"精品双人房"库存不足，请及时补充', 'PRODUCT_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '商品下架通知', '您的商品"豪华套房"已自动下架，原因：价格异常', 'PRODUCT_NOTIFICATION', 'high', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 更多订单通知
(1003, '订单提醒 #ORDER126', '订单即将超时，请尽快处理', 'ORDER_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '订单完成 #ORDER127', '订单ORDER127已完成，用户评分：5星', 'ORDER_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 更多系统通知
(1002, '系统升级通知', '系统将进行版本升级，新增多项功能', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '操作指南更新', '平台操作指南已更新，请查看最新版本', 'SYSTEM_NOTIFICATION', 'low', 'admin', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 更多促销通知
(1001, '双11活动预告', '双11购物节活动规则已发布，请查看', 'PROMOTION_NOTIFICATION', 'high', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '店铺优惠券投放', '系统自动为您投放了100张优惠券', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),

-- 补充到50条数据
(1001, '系统公告', '平台新增直播功能，欢迎体验', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '订单退款通知 #ORDER128', '订单ORDER128发起退款申请', 'ORDER_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '新功能上线通知', '商家后台新增数据分析功能', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '用户投诉提醒', '收到用户投诉，请及时处理', 'COMPLAINT_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '年度账单提醒', '您的2023年度账单已生成', 'ACCOUNT_NOTIFICATION', 'normal', 'finance', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '商品促销提醒', '您的商品已参与平台促销活动', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '订单超时提醒 #ORDER129', '订单ORDER129即将超时，请尽快处理', 'ORDER_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '系统更新提醒', '系统将于明日凌晨2点进行更新', 'SYSTEM_NOTIFICATION', 'high', 'admin', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '活动报名通知', '新年活动报名开始，请及时参与', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '账户异常提醒', '检测到异常登录，请确认是否为本人操作', 'ACCOUNT_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '订单评价提醒 #ORDER130', '订单ORDER130收到新评价', 'REVIEW_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '库存预警通知', '多个房型库存不足，请及时更新', 'PRODUCT_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '促销活动结算', '上月促销活动已完成结算', 'PROMOTION_NOTIFICATION', 'normal', 'finance', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '系统功能调整', '部分功能位置调整，点击查看详情', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '订单统计报告', '您的月度订单统计报告已生成', 'ORDER_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '商家认证提醒', '请尽快完成商家年度认证', 'ACCOUNT_NOTIFICATION', 'high', 'admin', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '用户反馈通知', '收到用户建议反馈，请查看', 'REVIEW_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '节日营销建议', '春节营销方案建议已生成', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '订单处理提醒 #ORDER131', '新订单ORDER131等待处理', 'ORDER_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '系统维护完成', '系统维护已完成，新功能已上线', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '优惠券到期提醒', '部分优惠券即将过期', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '账期结算通知', '本期账单已生成，请查收', 'ACCOUNT_NOTIFICATION', 'normal', 'finance', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '房态��新提醒', '请及时更新节假日房态', 'PRODUCT_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '订单取消通知 #ORDER132', '订单ORDER132已被用户取消', 'ORDER_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '活动报名截止', '五一活动报名即将截止', 'PROMOTION_NOTIFICATION', 'high', 'marketing', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '系统问题修复', '之前反馈的系统问题已修复', 'SYSTEM_NOTIFICATION', 'normal', 'admin', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '订单改期申请 #ORDER133', '订单ORDER133申请改期，请处理', 'ORDER_NOTIFICATION', 'high', 'system', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1001, '营销数据分析', '上月营销活动分析报告已生成', 'PROMOTION_NOTIFICATION', 'normal', 'marketing', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1002, '发票申请提醒', '新的发票申请待处理', 'ACCOUNT_NOTIFICATION', 'normal', 'finance', 0, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)),
(1003, '订单完成通知 #ORDER134', '订单ORDER134已完成，等待评价', 'ORDER_NOTIFICATION', 'normal', 'system', 1, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)); 