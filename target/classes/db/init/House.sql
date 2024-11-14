-- 房源表
CREATE TABLE `t_house` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '房源ID',
                           `name` varchar(100) NOT NULL COMMENT '房源名称',
                           `type` varchar(50) NOT NULL COMMENT '房源类型',
                           `address` varchar(255) NOT NULL COMMENT '房源地址',
                           `city` varchar(50) NOT NULL COMMENT '所在城市',
                           `district` varchar(50) NOT NULL COMMENT '所在地区',
                           `location` varchar(255) NOT NULL COMMENT '详细位置',
                           `price` decimal(10,2) NOT NULL COMMENT '价格/晚',
                           `max_guests` int NOT NULL COMMENT '最大入住人数',
                           `description` text COMMENT '房源描述',
                           `category` varchar(50) DEFAULT NULL COMMENT '房源分类',
                           `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0:下架 1:上架)',
                           `merchant_id` bigint NOT NULL COMMENT '商家ID',
                           `merchant_name` varchar(100) NOT NULL COMMENT '商家名称',
                           `facilities` json DEFAULT NULL COMMENT '设施列表(JSON数组)',
                           `images` json DEFAULT NULL COMMENT '图片列表(JSON数组)',
                           `image` varchar(255) DEFAULT NULL COMMENT '房源主图',
                           `rating` decimal(2,1) DEFAULT '5.0' COMMENT '评分',
                           `features` json DEFAULT NULL COMMENT '特色标签(JSON数组)',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
                           PRIMARY KEY (`id`),
                           KEY `idx_merchant_id` (`merchant_id`),
                           KEY `idx_status` (`status`),
                           KEY `idx_city` (`city`),
                           KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房源表';

-- 插入测试数据
INSERT INTO `t_house` (
    `name`, `type`, `address`, `city`, `district`, `location`,
    `price`, `max_guests`, `description`, `category`, `status`,
    `merchant_id`, `merchant_name`, `facilities`, `images`,
    `image`, `rating`, `features`
) VALUES (
             '海景度假别墅', '别墅', '海南省三亚市海棠区滨海大道', '三亚', '海棠区', '近海棠湾沙滩',
             1288.00, 6, '270°一线海景房,独立泳池,私人沙滩', '度假别墅', 1,
             1001, '阳光度假酒店',
             '["wifi","停车场","厨房","洗衣机","空调","游泳池"]',
             '["http://example.com/villa1.jpg","http://example.com/villa2.jpg"]',
             'http://example.com/villa_main.jpg',
             4.8,
             '["海景","泳池","度假"]'
         );

-- 插入多条房源测试数据
INSERT INTO `t_house` (
    `name`, `type`, `address`, `city`, `district`, `location`,
    `price`, `max_guests`, `description`, `category`, `status`,
    `merchant_id`, `merchant_name`, `facilities`, `images`,
    `image`, `rating`, `features`
) VALUES
-- 三亚的房源
(
    '椰梦长廊海景公寓', '公寓', '海南省三亚市三亚湾路128号', '三亚', '吉阳区', '三亚湾椰梦长廊',
    688.00, 4, '一线海景房,步行2分钟到沙滩,近三亚湾商圈', '精品公寓', 1,
    1001, '椰子湾度假公寓',
    '["wifi","停车场","厨房","洗衣机","空调","电梯"]',
    '["http://example.com/sanya1.jpg","http://example.com/sanya2.jpg"]',
    'http://example.com/sanya_main.jpg',
    4.9,
    '["海景","沙滩","商圈"]'
),
(
    '亚龙湾温泉别墅', '别墅', '海南省三亚市亚龙湾国家旅游度假区', '三亚', '天涯区', '亚龙湾',
    2588.00, 8, '独栋温泉别墅,私家泳池,管家服务', '豪华别墅', 1,
    1002, '亚龙湾度假村',
    '["wifi","停车场","厨房","洗衣机","空调","泳池","温泉","管家"]',
    '["http://example.com/yalong1.jpg","http://example.com/yalong2.jpg"]',
    'http://example.com/yalong_main.jpg',
    4.8,
    '["温泉","泳池","奢华"]'
),

-- 北京的房源
(
    '故宫旁四合院', '四合院', '北京市东城区景山前街', '北京', '东城区', '故宫博物院附近',
    1988.00, 6, '百年老宅,典型北京四合院,近故宫天安门', '特色民宿', 1,
    1003, '京城文化民宿',
    '["wifi","暖气","茶室","庭院","空调"]',
    '["http://example.com/beijing1.jpg","http://example.com/beijing2.jpg"]',
    'http://example.com/beijing_main.jpg',
    4.7,
    '["文化","古韵","地标"]'
),
(
    '三里屯SOHO公寓', '公寓', '北京市朝阳区三里屯', '北京', '朝阳区', '三里屯商圈',
    799.00, 2, '时尚公寓,俯瞰三里屯,近地铁', '商务公寓', 1,
    1004, '城市精品公寓',
    '["wifi","健身房","停车场","电梯","智能门锁"]',
    '["http://example.com/sanlitun1.jpg","http://example.com/sanlitun2.jpg"]',
    'http://example.com/sanlitun_main.jpg',
    4.6,
    '["时尚","商圈","交通"]'
),

-- 上海的房源
(
    '外滩江景套房', '公寓', '上海市黄浦区中山东一路', '上海', '黄浦区', '外滩',
    1288.00, 3, '270°外滩江景房,近南京路步行街', '豪华公寓', 1,
    1005, '浦江夜景公寓',
    '["wifi","停车场","健身房","空调","智能家电"]',
    '["http://example.com/waitan1.jpg","http://example.com/waitan2.jpg"]',
    'http://example.com/waitan_main.jpg',
    4.9,
    '["江景","地标","夜景"]'
),
(
    '田子坊老洋房', '洋房', '上海市黄浦区泰康路', '上海', '黄浦区', '田子坊',
    1588.00, 4, '百年石库门建筑,近地铁,文艺氛围浓厚', '特色民宿', 1,
    1006, '老上海文化民宿',
    '["wifi","庭院","茶室","麻将室","空调"]',
    '["http://example.com/tianzifang1.jpg","http://example.com/tianzifang2.jpg"]',
    'http://example.com/tianzifang_main.jpg',
    4.7,
    '["文艺","怀旧","特色"]'
),

-- 成都的房源
(
    '宽窄巷子民宿', '民居', '成都市青羊区宽窄巷子', '成都', '青羊区', '宽窄巷子',
    468.00, 4, '老成都民居,近太古里,春熙路商圈', '特色民宿', 1,
    1007, '蓉城民宿',
    '["wifi","麻将室","茶室","空调","电视"]',
    '["http://example.com/chengdu1.jpg","http://example.com/chengdu2.jpg"]',
    'http://example.com/chengdu_main.jpg',
    4.8,
    '["文化","美食","休闲"]'
),
(
    '天府新区复式公寓', '公寓', '成都市天府新区天府大道', '成都', '天府新区', '天府软件园',
    528.00, 3, '现代简约风格,近环球中心,软件园', '商务公寓', 1,
    1008, '天府商务公寓',
    '["wifi","健身房","停车场","电梯","智能门锁"]',
    '["http://example.com/tianfu1.jpg","http://example.com/tianfu2.jpg"]',
    'http://example.com/tianfu_main.jpg',
    4.6,
    '["商务","现代","便利"]'
),

-- 杭州的房源
(
    '西湖湖景别墅', '别墅', '杭州市西湖区北山街道', '杭州', '西湖区', '西湖景区',
    2088.00, 6, '临湖独栋别墅,近断桥残雪', '豪华别墅', 1,
    1009, '西湖度假别墅',
    '["wifi","泳池","花园","厨房","停车场","管家"]',
    '["http://example.com/xihu1.jpg","http://example.com/xihu2.jpg"]',
    'http://example.com/xihu_main.jpg',
    4.9,
    '["湖景","度假","奢华"]'
),
(
    '钱塘江观景公寓', '公寓', '杭州市滨江区江南大道', '杭州', '滨江区', '钱塘江畔',
    688.00, 2, '江景房,近阿里巴巴总部,适合商务出行', '商务公寓', 1,
    1010, '钱塘公寓',
    '["wifi","健身房","停车场","电梯","办公桌"]',
    '["http://example.com/qiantang1.jpg","http://example.com/qiantang2.jpg"]',
    'http://example.com/qiantang_main.jpg',
    4.7,
    '["江景","商务","科技"]'
);

-- 插入一些下架状态的房源
INSERT INTO `t_house` (
    `name`, `type`, `address`, `city`, `district`, `location`,
    `price`, `max_guests`, `description`, `category`, `status`,
    `merchant_id`, `merchant_name`, `facilities`, `images`,
    `image`, `rating`, `features`
) VALUES
      (
          '装修中的海景房', '公寓', '三亚市海棠区海棠北路', '三亚', '海棠区', '海棠湾',
          888.00, 4, '正在装修中,预计下月开放预订', '精品公寓', 0,
          1001, '椰子湾度假公寓',
          '["wifi","停车场","厨房","洗衣机","空调"]',
          '["http://example.com/temp1.jpg","http://example.com/temp2.jpg"]',
          'http://example.com/temp_main.jpg',
          0.0,
          '["海景","新装修"]'
      ),
      (
          '待验收别墅', '别墅', '北京市顺义区后沙峪', '北京', '顺义区', '温泉度假区',
          1688.00, 6, '正在进行最终验收,即将开放', '度假别墅', 0,
          1003, '京城文化民宿',
          '["wifi","泳池","温泉","停车场","花园"]',
          '["http://example.com/pending1.jpg","http://example.com/pending2.jpg"]',
          'http://example.com/pending_main.jpg',
          0.0,
          '["温泉","度假"]'
      );