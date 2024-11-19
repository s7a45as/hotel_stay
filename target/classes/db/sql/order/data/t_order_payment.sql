-- 插入订单支付测试数据
INSERT INTO `t_order_payment` (
    `order_id`, `payment_no`, `payment_method`, `amount`,
    `status`, `pay_time`, `refund_time`, `refund_amount`, `refund_reason`
) VALUES
-- 已支付订单的支付记录
('ORD202401010001', 'PAY202401010001', 'ALIPAY', 1376.00,
 1, '2024-01-01 10:30:00', NULL, NULL, NULL),

('ORD202401010002', 'PAY202401010002', 'WECHAT', 2576.00,
 1, '2024-01-01 14:20:00', NULL, NULL, NULL),

-- 待支付订单的支付记录
('ORD202401020001', 'PAY202401020001', 'ALIPAY', 3976.00,
 0, NULL, NULL, NULL, NULL),

('ORD202401020002', 'PAY202401020002', 'WECHAT', 468.00,
 0, NULL, NULL, NULL, NULL),

-- 已取消订单的支付记录
('ORD202401030001', 'PAY202401030001', 'ALIPAY', 4176.00,
 2, NULL, NULL, NULL, NULL),

-- 已完成订单的支付记录
('ORD202312010001', 'PAY202312010001', 'WECHAT', 5176.00,
 1, '2023-12-23 16:40:00', NULL, NULL, NULL),

-- 已退款订单的支付记录
('ORD202312010002', 'PAY202312010002', 'ALIPAY', 2397.00,
 3, '2023-12-24 09:15:00', '2023-12-25 10:20:00', 2397.00, '客户投诉服务质量'); 