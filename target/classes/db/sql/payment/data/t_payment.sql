-- 插入支付测试数据
INSERT INTO `t_payment` (
    `order_id`, `amount`, `method`, `status`,
    `qrcode`, `pay_time`, `expire_time`
) VALUES
-- 已支付的订单
('ORD202401010001', 1376.00, 'ALIPAY', 'PAID',
 'https://example.com/qr/pay001.png', '2024-01-01 10:30:00',
 '2024-01-01 11:00:00'),
 
('ORD202401010002', 2576.00, 'WECHAT', 'PAID',
 'https://example.com/qr/pay002.png', '2024-01-01 14:20:00',
 '2024-01-01 15:00:00'),

-- 待支付的订单
('ORD202401020001', 3976.00, 'ALIPAY', 'PENDING',
 'https://example.com/qr/pay003.png', NULL,
 DATE_ADD(NOW(), INTERVAL 30 MINUTE)),
 
('ORD202401020002', 468.00, 'WECHAT', 'PENDING',
 'https://example.com/qr/pay004.png', NULL,
 DATE_ADD(NOW(), INTERVAL 30 MINUTE)),

-- 已取消的订单
('ORD202401030001', 4176.00, 'ALIPAY', 'CANCELLED',
 'https://example.com/qr/pay005.png', NULL,
 '2024-01-03 11:00:00'),

-- 已完成的订单
('ORD202312010001', 5176.00, 'WECHAT', 'PAID',
 'https://example.com/qr/pay006.png', '2023-12-23 16:40:00',
 '2023-12-23 17:00:00'),
 
('ORD202312010002', 2397.00, 'ALIPAY', 'PAID',
 'https://example.com/qr/pay007.png', '2023-12-24 09:15:00',
 '2023-12-24 10:00:00'); 