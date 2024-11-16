CREATE TABLE `t_destination` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '目的地ID',
                                 `name` varchar(100) NOT NULL COMMENT '目的地名称',
                                 `city_code` varchar(50) NOT NULL COMMENT '城市代码',
                                 `image` varchar(255) DEFAULT NULL COMMENT '图片URL',
                                 `description` text COMMENT '描述',
                                 `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用 1:启用)',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_city_code` (`city_code`) COMMENT '城市代码索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='热门目的地表';

-- 插入更多示例数据
INSERT INTO `t_destination` (`name`, `city_code`, `image`, `description`, `status`) VALUES
-- 北京景点
('故宫', 'BJ', 'https://example.com/forbidden-city.jpg', '北京故宫，中国最大的古代宫殿建筑群', 1),
('长城', 'BJ', 'https://example.com/great-wall.jpg', '万里长城，中华文明的象征', 1),
('颐和园', 'BJ', 'https://example.com/summer-palace.jpg', '中国现存最大的皇家园林', 1),
('天坛', 'BJ', 'https://example.com/temple-of-heaven.jpg', '明清两代帝王祭天的场所', 1),

-- 上海景点
('外滩', 'SH', 'https://example.com/bund.jpg', '上海外滩，万国建筑博览群', 1),
('东方明珠', 'SH', 'https://example.com/pearl-tower.jpg', '上海的城市地标之一', 1),
('豫园', 'SH', 'https://example.com/yu-garden.jpg', '江南古典园林的代表作', 1),
('迪士尼', 'SH', 'https://example.com/disney.jpg', '亚洲最大的迪士尼乐园', 1),

-- 杭州景点
('西湖', 'HZ', 'https://example.com/westlake.jpg', '杭州西湖，中国十大风景名胜之一', 1),
('灵隐寺', 'HZ', 'https://example.com/lingyin.jpg', '江南佛教寺院之一', 1),
('宋城', 'HZ', 'https://example.com/songcheng.jpg', '再现南宋繁华景象的主题公园', 1),
('千岛湖', 'HZ', 'https://example.com/qiandao-lake.jpg', '人工湖泊，千岛之城', 1),

-- 成都景点
('大熊猫基地', 'CD', 'https://example.com/panda.jpg', '世界著名的大熊猫繁育研究基地', 1),
('锦里古街', 'CD', 'https://example.com/jinli.jpg', '成都最古老的商业街之一', 1),
('都江堰', 'CD', 'https://example.com/dujiangyan.jpg', '世界文化遗产，古代水利工程', 1),
('春熙路', 'CD', 'https://example.com/chunxi.jpg', '成都最繁华的商业步行街', 1),

-- 三亚景点
('亚龙湾', 'SYA', 'https://example.com/yalong-bay.jpg', '号称"东方夏威夷"的度假胜地', 1),
('天涯海角', 'SYA', 'https://example.com/tianya.jpg', '浪漫文化景区，情侣必游之地', 1),
('南山文化旅游区', 'SYA', 'https://example.com/nanshan.jpg', '佛教文化与热带风光的结合', 1),
('蜈支洲岛', 'SYA', 'https://example.com/wuzhizhou.jpg', '被誉为"东方马尔代夫"', 1),

-- 丽江景点
('古城', 'LJ', 'https://example.com/lijiang-old-town.jpg', '世界文化遗产，纳西族古城', 1),
('玉龙雪山', 'LJ', 'https://example.com/jade-dragon.jpg', '云南第一峰，冰川奇观', 1),
('拉市海', 'LJ', 'https://example.com/lashi-lake.jpg', '高原湿地公园，候鸟栖息地', 1),
('束河古镇', 'LJ', 'https://example.com/shuhe.jpg', '茶马古道重要驿站', 1),

-- 西安景点
('兵马俑', 'XA', 'https://example.com/terracotta.jpg', '世界第八大奇迹', 1),
('大雁塔', 'XA', 'https://example.com/giant-wild-goose.jpg', '唐代佛教建筑的代表', 1),
('回民街', 'XA', 'https://example.com/muslim-street.jpg', '西安最著名的美食街', 1),
('城墙', 'XA', 'https://example.com/city-wall.jpg', '世界上最完整的古代城墙', 1),

-- 暂时禁用的景点（用于测试）
('施工中的景区', 'BJ', 'https://example.com/construction.jpg', '正在进行改造升级，暂时关闭', 0),
('临时关闭景点', 'SH', 'https://example.com/closed.jpg', '因维护暂时关闭', 0),
('待开发景区', 'CD', 'https://example.com/developing.jpg', '正在开发中，即将开放', 0),
('季节性景点', 'HZ', 'https://example.com/seasonal.jpg', '冬季闭园，春季开放', 0);