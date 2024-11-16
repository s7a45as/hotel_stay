CREATE TABLE `t_homecity` (
  `value` varchar(50) NOT NULL COMMENT '城市代码',
  `label` varchar(100) NOT NULL COMMENT '城市名称',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用 1:启用)',
  PRIMARY KEY (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='城市信息表';

-- 插入更多示例数据
INSERT INTO `t_homecity` (`value`, `label`, `status`) VALUES 
-- 一线城市
('BJ', '北京', 1),
('SH', '上海', 1),
('GZ', '广州', 1),
('SZ', '深圳', 1),

-- 东部沿海城市
('HZ', '杭州', 1),
('NJ', '南京', 1),
('SUZ', '苏州', 1),  -- 改为SUZ避免与深圳SZ重复
('NB', '宁波', 1),
('WX', '无锡', 1),
('QD', '青岛', 1),
('DL', '大连', 1),
('XM', '厦门', 1),

-- 中西部重要城市
('CD', '成都', 1),
('CQ', '重庆', 1),
('WH', '武汉', 1),
('CS', '长沙', 1),
('ZZ', '郑州', 1),
('XA', '西安', 1),
('KM', '昆明', 1),

-- 南部城市
('GL', '桂林', 1),
('SYA', '三亚', 1),  -- 改为SYA避免与沈阳SY重复
('GY', '贵阳', 1),   -- 改为GY避免与广州GZ重复
('NN', '南宁', 1),
('FZ', '福州', 1),

-- 北部城市
('HRB', '哈尔滨', 1),
('CC', '长春', 1),
('SY', '沈阳', 1),
('TJ', '天津', 1),
('JN', '济南', 1),

-- 特别行政区
('HK', '香港', 1),
('MO', '澳门', 1),

-- 著名旅游城市
('LJ', '丽江', 1),
('DLI', '大理', 1),  -- 改为DLI避免与大连DL重复
('XSL', '香格里拉', 1),
('WZH', '乌镇', 1),
('ZJJ', '张家界', 1),
('QHD', '秦皇岛', 1),
('YT', '烟台', 1),
('WEH', '威海', 1),  -- 改为WEH避免与武汉WH重复
('YNT', '延庆', 1),

-- 部分禁用的城市（用于测试）
('LS', '拉萨', 0),
('WLQ', '乌鲁木齐', 0),
('BY', '白银', 0),
('AKS', '阿克苏', 0); 